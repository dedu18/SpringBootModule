package com.fht;

import com.ca.icbcinterface.model.icbcImport.PayOrRefundReqModel;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class Refund {
    public static final MediaType JSON;
    public static OkHttpClient client;
    public static InputStreamReader is;
    public static BufferedReader br;
    public static Gson gson;

    static {
        JSON = MediaType.parse("application/json; charset=utf-8");
        client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS).build();
        is = new InputStreamReader(System.in);
        br = new BufferedReader(is);
        gson = new Gson();
    }

    public static void main(String[] args) {
        String orderNo = read("orderNo->");
        String payId = read("payId->");
        String vendorId = read("vendorId->");
        String ip = read("ip->");
        String port = read("port->");
        PayOrRefundReqModel payOrRefundReqModelVo = new PayOrRefundReqModel();
        payOrRefundReqModelVo.setOperateType(1);
        payOrRefundReqModelVo.setOrderNo(orderNo);
        payOrRefundReqModelVo.setPayId(payId);
        payOrRefundReqModelVo.setVendorId(vendorId);
        String url = "http://" + ip + ":" + port + "/icbc/import/payOrRefund";
        try {
            String payOrRefundReqModel = gson.toJson(payOrRefundReqModelVo);
            System.out.println("[发起请求][url-{" + url + "}, payOrRefundReqModel-{" + payOrRefundReqModel + "}]");
            String result = post(url, payOrRefundReqModel);
            System.out.println("[请求成功][result-{" + result + "}]");
        } catch (IOException e) {
            System.out.println("POST请求失败:");
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String read(String message) {
        System.out.print(message);
        String name = null;
        try {
            name = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}

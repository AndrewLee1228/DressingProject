package com.dressing.dressingproject.manager;

import android.content.Context;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.models.CodiModel;
import com.dressing.dressingproject.ui.models.CodiProducts;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.models.VersionModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 15. 11. 3.
 */
public class NetworkManager {

    private static NetworkManager instance;
    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    AsyncHttpClient client;
    private NetworkManager() {

//        try {
//            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            trustStore.load(null, null);
//            MySSLSocketFactory socketFactory = new MySSLSocketFactory(trustStore);
//            socketFactory.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client = new AsyncHttpClient();
//            client.setSSLSocketFactory(socketFactory);
//            client.setCookieStore(new PersistentCookieStore(ApplicationLoader.getContext()));
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (CertificateException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        } catch (UnrecoverableKeyException e) {
//            e.printStackTrace();
//        }
//
//
//        client.setCookieStore(new PersistentCookieStore(ApplicationLoader.getContext()));
    }

    public HttpClient getHttpClient() {
        return client.getHttpClient();
    }

    public interface OnResultListener<T> {
        public void onSuccess(T result);
        public void onFail(int code);
    }

    //서버주소
    private static final String SERVER = "http://openapi.naver.com";


    //상세코디 요청
    private static final String DETAIL_CODI_URL = SERVER + "/search";

    public void getNetworkDetailCodi(Context context, final OnResultListener<CodiProducts> listener) {
        final RequestParams params = new RequestParams();

        client.get(DETAIL_CODI_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                CodiProducts codiProducts = getCodiProductList();
                listener.onSuccess(codiProducts);
            }
        });
    }


    /*기존에 있던거*/

    private static List<CodiModel> codiitems = new ArrayList<>();
    private static ArrayList<ProductModel> productItems = new ArrayList<>();
    private static List<String> list = new ArrayList<String>();

    static {
        for (int i = 1; i <= 10; i++) {
            codiitems.add(new CodiModel("Item " + i, "http://lorempixel.com/500/500/animals/" ));
        }
        int[] ids = {R.drawable.test_codi2_1,R.drawable.test_codi2_2,R.drawable.test_codi2_3,R.drawable.test_codi2_4};
        for (int i = 1; i <= ids.length; i++) {
            String produtcName ="product "+ i;
            String productBrandName="test";
            String productPrice="40000";
            String productLocation="test";
            String productImgURL= Integer.toString(ids[i-1]);
            String mapURL="test";
            boolean isFavorite=false;
            productItems.add(new ProductModel(produtcName,productBrandName,productPrice,productLocation,productImgURL,mapURL,isFavorite));
        }


        for (int i = 0; i < VersionModel.data.length; i++) {
            list.add(VersionModel.data[i]);
        }
    }

    public static List<CodiModel> getRecommendList() {
        return codiitems;
    }

    public static CodiProducts getCodiProductList() {
        CodiProducts codiProducts = new CodiProducts();
        codiProducts.items = productItems;
        return codiProducts;
    }

    public static List<String> getList() {
        return list;
    }

    /*기존에 있던거*/

    public void cancelAll(Context context) {
        client.cancelRequests(context, true);
    }
}

package com.dressing.dressingproject.manager;

import android.content.Context;
import android.widget.Toast;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.models.AnalysisResult;
import com.dressing.dressingproject.ui.models.CodiModel;
import com.dressing.dressingproject.ui.models.CodiResult;
import com.dressing.dressingproject.ui.models.CoordinationResult;
import com.dressing.dressingproject.ui.models.EstimationResult;
import com.dressing.dressingproject.ui.models.FavoriteCodiResult;
import com.dressing.dressingproject.ui.models.FavoriteProductResult;
import com.dressing.dressingproject.ui.models.FavoriteResult;
import com.dressing.dressingproject.ui.models.FitResult;
import com.dressing.dressingproject.ui.models.PostEstimationResult;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.models.ProductResult;
import com.dressing.dressingproject.ui.models.RecommendCodiResult;
import com.dressing.dressingproject.ui.models.SignInResult;
import com.dressing.dressingproject.ui.models.SignUpResult;
import com.dressing.dressingproject.ui.models.SucessResult;
import com.dressing.dressingproject.ui.models.UserItem;
import com.dressing.dressingproject.ui.models.VersionModel;
import com.google.gson.Gson;
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
    private final Gson gson;

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    AsyncHttpClient client;
    private NetworkManager() {

//        try {
//            KeyStore trustStore = KeyStore.newInstance(KeyStore.getDefaultType());
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
            gson = new Gson();
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
    private static final String SERVER = "http://54.64.106.31";


    //TODO:로그인 요청
    private static final String SIGNIN_URL = SERVER + "/member/login";

    public void requestPostSignin(Context context, UserItem item,final OnResultListener<SignInResult> onResultListener) {
        RequestParams params = new RequestParams();
        params.put("email", item.getEmail());
        params.put("password", item.getNick());

        client.post(context, SIGNIN_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                SignInResult signInResult = gson.fromJson(responseString, SignInResult.class);
                onResultListener.onSuccess(signInResult);
            }
        });
    }


    //TODO:회원정보수정
    private static final String MEMBER_URL = SERVER + "/member";

    public void requestPutUserInfoChange(Context context,UserItem item, final OnResultListener<SucessResult> onResultListener)
    {
        RequestParams params = new RequestParams();
        params.put("nickname",item.getNick());
        params.put("password",item.getPassword());
        params.put("areaName","");
        params.put("areaDetailName","");
        params.put("snsKind","");
        params.put("snsToken","");

        client.put(context, MEMBER_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                SucessResult sucessResult = gson.fromJson(responseString, SucessResult.class);
                onResultListener.onSuccess(sucessResult);
            }
        });
    }


    //TODO:회원탈퇴

    public void requestDeleteUser(Context context, final OnResultListener<SucessResult> onResultListener)
    {
        client.delete(context, MEMBER_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                SucessResult sucessResult = gson.fromJson(responseString, SucessResult.class);
                onResultListener.onSuccess(sucessResult);
            }
        });
    }

    //TODO:회원가입요청

    public void requestPostSignUp(Context context,UserItem item, final OnResultListener<SignUpResult> onResultListener) {
        RequestParams params = new RequestParams();
        params.put("Email", item.getEmail());
        params.put("Nickname", item.getNick());
        params.put("Password", item.getPassword());
        params.put("areaNum", "");
        params.put("areaDetailNum", "");

        client.post(context, MEMBER_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                SignUpResult signUpResult = gson.fromJson(responseString, SignUpResult.class);
                onResultListener.onSuccess(signUpResult);
            }
        });
    }

    //TODO:스타일평가 코디목록

    private static final String COORDINATION_URL = SERVER + "/coordination";

    public void requestGetCoordination(Context context,int start, int display, final OnResultListener<CoordinationResult> codiItemsOnResultListener) {
        final RequestParams params = new RequestParams();
        params.put("start", start);
        params.put("display", display);
        client.get(context, COORDINATION_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                codiItemsOnResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                CoordinationResult coordinationResult = gson.fromJson(responseString, CoordinationResult.class);
//                int code = coordinationResult.code;
//                String msg = coordinationResult.msg;
//                ArrayList<CoordinationItem> coordinationList = coordinationResult.coordinationList;
//                for(CoordinationItem coordinationItem : coordinationList)
//                {
//                    int coordinationNum = coordinationItem.coordinationNum;
//                    String keyword = coordinationItem.keyword;
//                    String comment = coordinationItem.comment;
//                    String coordinationImg = coordinationItem.coordinationImg;
//                    int estimationScore = coordinationItem.estimationScore;
//                    int foreseeScore = coordinationItem.foreseeScore;
//                }

                codiItemsOnResultListener.onSuccess(coordinationResult);
            }
        });
    }

    //TODO:평가한 코디목록
    private static final String ESTIMATION_URL = SERVER + "/estimation";

    public void requestGetEstimation(Context context,int start, int display, final OnResultListener<EstimationResult> codiItemsOnResultListener) {
        final RequestParams params = new RequestParams();
        params.put("start", start);
        params.put("display", display);
        client.get(context,ESTIMATION_URL, params,new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                codiItemsOnResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                EstimationResult estimationResult = gson.fromJson(responseString, EstimationResult.class);
//                int code = estimationResult.code;
//                String msg = estimationResult.msg;
//                ArrayList<CoordinationItem> coordinationItems = estimationResult.coordinationList;

                codiItemsOnResultListener.onSuccess(estimationResult);
            }
        });
    }

    //TODO:선호취향 평가
    public void requestPostEstimation(final Context context,final float ratting ,final CodiModel codiModel, final OnResultListener<PostEstimationResult> onResultListener) {
        final RequestParams params = new RequestParams();
        params.put("coordinationNum", codiModel.getCodiNum());
        params.put("estimationScore", ratting);
        client.post(context, ESTIMATION_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                PostEstimationResult codiScoreResult = gson.fromJson(responseString, PostEstimationResult.class);
//                int code = estimationResult.code;
//                String msg = estimationResult.msg;
//                ArrayList<CoordinationItem> coordinationItems = estimationResult.coordinationList;
                //네이버 무비즈 객체와 같이 result 객체를 만들어 결과상태와 결과값 객체를 가지는 놈!
                Toast.makeText(context, codiModel.getTitle() + " 평가되었습니다!", Toast.LENGTH_SHORT).show();
                PostEstimationResult postEstimationResult = gson.fromJson(responseString, PostEstimationResult.class);
                postEstimationResult.setRating(ratting);

                onResultListener.onSuccess(codiScoreResult);
            }
        });
    }

    //TODO:유저취향분석
    private static final String USER_ANALYSIS_URL = SERVER + "/analysis";

    public void requestGetUserAnalysis(Context context, final OnResultListener<AnalysisResult> onResultListener) {

        client.get(context, USER_ANALYSIS_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                AnalysisResult analysisResult = gson.fromJson(responseString, AnalysisResult.class);
//                int code = analysisResult.code;
//                String msg = analysisResult.msg;
//                ArrayList<AnalysisData> list = analysisResult.list;
//                String kind = AnalysisData.kind;
//                int value = AnalysisData.value;
                onResultListener.onSuccess(analysisResult);
            }
        });
    }

    //TODO:추천코디보기
    private static final String RECOMMEND_CODI_URL = SERVER + "/recommend";

    public void requestGetRecommendCodi(Context context, final OnResultListener<RecommendCodiResult> onResultListener) {
        RequestParams params = new RequestParams();

        client.get(context, RECOMMEND_CODI_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                RecommendCodiResult recommendCodiResult = gson.fromJson(responseString, RecommendCodiResult.class);
//                int code = recommendCodiResult.code;
//                String msg = recommendCodiResult.msg;
//                ArrayList<CoordinationItem> list = recommendCodiResult.list;

                onResultListener.onSuccess(recommendCodiResult);
            }
        });
    }


    //TODO: 연관상품 요청
    private static final String DETAIL_CODI_URL = SERVER + "/item";

    public void requestGetDetailCodi(Context context,CodiModel codiModel, final OnResultListener<ProductResult> onResultListener) {
        RequestParams params = new RequestParams();
        params.put("coordinationNum", codiModel.getCodiNum());

        client.get(context,DETAIL_CODI_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ProductResult productResult = gson.fromJson(responseString, ProductResult.class);
//                int code = productResult.code;
//                String msg = productResult.msg;
                onResultListener.onSuccess(productResult);
            }
        });
    }

    //TODO: 코디, 상품 찜(Favorite)요청
    private static final String FAVORITE_URL = SERVER + "/selected";

    public void requestPostCodiFavorite(final Context context,CodiModel codiModel, final OnResultListener<FavoriteResult> onResultListener)
    {

        RequestParams params = new RequestParams();
        params.add("coordinationNum",codiModel.getCodiNum());

        client.post(context, FAVORITE_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                // statusCode는 네트워크 연결에대한 체크를 할 수 있고
                // 서버쪽 db조회 성공여부등의 확인은 responseString을 파싱하여 알 수 있다.
                // onSuccess에 매개변수로 파싱한 객체를 넘겨주어 서버쪽 프로세스가 잘 동작하여
                // 받은 정보가 필요한 정보인지 확인을 한다.


                FavoriteResult favoriteResult = gson.fromJson(responseString, FavoriteResult.class);
//                int code = favoriteResult.code;
//                String msg = favoriteResult.msg;
                favoriteResult.setSelectedState(true);
                onResultListener.onSuccess(favoriteResult);
            }
        });

    }

    //상품 Favorite 요청

    public void requestPostProductFavorite(final Context context,ProductModel item, final OnResultListener<FavoriteResult> productFavoriteResultOnResultListener) {


        RequestParams params = new RequestParams();
        params.add("itemNum",item.getProductNum());

        client.post(context, FAVORITE_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                productFavoriteResultOnResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                // statusCode는 네트워크 연결에대한 체크를 할 수 있고
                // 서버쪽 db조회 성공여부등의 확인은 responseString을 파싱하여 알 수 있다.
                // onSuccess에 매개변수로 파싱한 객체를 넘겨주어 서버쪽 프로세스가 잘 동작하여
                // 받은 정보가 필요한 정보인지 확인을 한다.


                FavoriteResult favoriteResult = gson.fromJson(responseString, FavoriteResult.class);
                favoriteResult.setSelectedState(true);
                productFavoriteResultOnResultListener.onSuccess(favoriteResult);
            }
        });
    }

    //TODO:찜해제

    private static final String DELETE_FAVORITE_URL = SERVER + "/selected/%s";

    public void requestDeleteFavorite(final Context context,ProductModel productModel,CodiModel codiModel, final OnResultListener<SucessResult> onResultListener) {

        String num = "";
        if(productModel != null)
        {
            num =productModel.getProductNum();
        }
        else
        {
            num =codiModel.getCodiNum();
        }
        String url = String.format(DELETE_FAVORITE_URL,num);

        client.delete(context, url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {


                SucessResult sucessResult = gson.fromJson(responseString, SucessResult.class);
                onResultListener.onSuccess(sucessResult);
            }
        });
    }


    //상세상품 요청
    private static final String DETAIL_PRODUCT_URL = SERVER + "/search";

    public void getNetworkDetailProduct(Context context, final OnResultListener<CodiResult> productItemsOnResultListener) {
        RequestParams params = new RequestParams();

        client.get(context, DETAIL_CODI_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                productItemsOnResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                CodiResult codiResult = getProductItemsList();
                productItemsOnResultListener.onSuccess(codiResult);
            }
        });
    }


    //찜코디 리스트 요청
    //추천코디요청
    private static final String FAVORITE_CODI_URL = SERVER + "/search";

    public void requestGetFavoriteCodi(Context context, final OnResultListener<FavoriteCodiResult> productItemsOnResultListener) {
        RequestParams params = new RequestParams();

        client.get(context, FAVORITE_CODI_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                productItemsOnResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                FavoriteCodiResult favoriteCodiResult = getFavoriteCodiList();
                productItemsOnResultListener.onSuccess(favoriteCodiResult);
            }
        });
    }
    //찜상품 리스트 요청
    private static final String FAVORITE_PODUCT_URL = SERVER + "/search";

    public void requestGetFavoriteProduct(Context context, final OnResultListener<FavoriteProductResult> productItemsOnResultListener) {
        RequestParams params = new RequestParams();

        client.get(context, FAVORITE_PODUCT_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                productItemsOnResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                FavoriteProductResult favoriteCodiResult = getFavoriteProductItemsList();
                productItemsOnResultListener.onSuccess(favoriteCodiResult);
            }
        });
    }


    //코디 fit 요청
    private static final String FIT_CODI_URL = SERVER + "/search";

    public void requestGetFitCodi(Context context,final CodiModel codiModel, final OnResultListener<FitResult> onResultListener) {
        RequestParams params = new RequestParams();

        client.get(context, FIT_CODI_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                FitResult fitCodiResult = new FitResult();
                fitCodiResult.setFit(codiModel.isFit());
                onResultListener.onSuccess(fitCodiResult);
            }
        });
    }

    //상품 fit 요청
    private static final String FIT_PRODUCT_URL = SERVER + "/search";

    public void requestGetFitProduct(Context context,final ProductModel productModel,final OnResultListener<FitResult> onResultListener)
    {
        RequestParams params = new RequestParams();

        client.get(context, FIT_PRODUCT_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                FitResult fitCodiResult = new FitResult();
                fitCodiResult.setFit(productModel.isFit());
                onResultListener.onSuccess(fitCodiResult);
            }
        });
    }


    /*기존에 있던거*/

    private static List<CodiModel> codiitems = new ArrayList<>();
    private static ArrayList<ProductModel> codiData = new ArrayList<>();
    private static ArrayList<CodiModel> productData = new ArrayList<>();
    private static List<String> list = new ArrayList<String>();

    static {
        for (int i = 1; i <= 10; i++) {
            String title = "남자들아, 겨울이 오면\n이렇게 입어주자!";
            String descript = "코디설명 블라블라";
            String imageURL= Integer.toString(R.drawable.test_codi);
            String estimationScore="3.5";
            String userScore ="2.0";
            String codeNum = "1";
            boolean isFavorite=false;
            codiitems.add(new CodiModel(title,descript,imageURL,estimationScore,userScore,codeNum,isFavorite,false));
        }
        int[] ids = {R.drawable.test_codi2_1,R.drawable.test_codi2_2,R.drawable.test_codi2_3,R.drawable.test_codi2_4};
        for (int i = 1; i <= ids.length; i++) {
            String productTitle = "시리즈나인 도트니트";
            String produtcName ="product "+ i;
            String productBrandName="test";
            String productPrice="40000";
            String productLocation="test";
            String productImgURL= Integer.toString(ids[i-1]);
            String productLogoImgURL = Integer.toString(R.mipmap.ic_launcher);
            String mapURL="test";
            String productNum ="a00000";
            boolean isFavorite=false;
            boolean isFit=false;
            if (i % 2== 0) {
                isFavorite = true;
                isFit = true;
            }

            codiData.add(new ProductModel(productTitle,produtcName,productBrandName,productPrice,productLocation,productImgURL,productLogoImgURL,mapURL,productNum,isFavorite,isFit));
        }
        int[] productIds = {R.drawable.test_codi,R.drawable.test_codi2,R.drawable.test_codi,R.drawable.test_codi2};
        for (int i = 1; i <= productIds.length; i++) {
            String title = "남자들아, 겨울이 오면\n이렇게 입어주자!";
            String descript = "코디설명 블라블라";
            String imageURL= Integer.toString(productIds[i-1]);
            String estimationScore="3.5";
            String userScore ="";
            String codeNum = "1" ;
            boolean isFavorite=false;
            if (i%2 ==0) {
                userScore ="0.0";
                isFavorite = true;
                estimationScore ="0.0";
            }
            else
                userScore ="3.5";


            productData.add(new CodiModel(title,descript,imageURL,estimationScore,userScore,codeNum,isFavorite,false));
        }


        for (int i = 0; i < VersionModel.data.length; i++) {
            list.add(VersionModel.data[i]);
        }
    }

    public static List<CodiModel> getRecommendList() {
        return codiitems;
    }

    public static CodiResult getProductItemsList() {
        CodiResult codiResult = new CodiResult();
        codiResult.items = productData;
        return codiResult;
    }
    public static FavoriteProductResult getFavoriteProductItemsList() {
        FavoriteProductResult favoriteProductResult = new FavoriteProductResult();
        favoriteProductResult.items = codiData;
        return favoriteProductResult;
    }


    public static FavoriteCodiResult getFavoriteCodiList() {
        FavoriteCodiResult recommendCodiResult = new FavoriteCodiResult();
        ArrayList<CodiModel> tempList = new ArrayList<CodiModel>();
        tempList.addAll(productData);
        tempList.addAll(productData);
        recommendCodiResult.items = tempList;
        return recommendCodiResult;
    }

//    public static ProductResult getCodiItemsList() {
//        ProductResult codiItems = new ProductResult();
//        codiItems.items = codiData;
//        return codiItems;
//    }

    public static List<String> getList() {
        return list;
    }

    /*기존에 있던거*/

    public void cancelAll(Context context) {
        client.cancelRequests(context, true);
    }
}

package com.dressing.dressingproject.manager;

import android.content.Context;
import android.widget.Toast;

import com.dressing.dressingproject.ui.models.AnalysisResult;
import com.dressing.dressingproject.ui.models.BrandResult;
import com.dressing.dressingproject.ui.models.CodiModel;
import com.dressing.dressingproject.ui.models.CodiResult;
import com.dressing.dressingproject.ui.models.CoordinationResult;
import com.dressing.dressingproject.ui.models.EstimationResult;
import com.dressing.dressingproject.ui.models.FavoriteCodiResult;
import com.dressing.dressingproject.ui.models.FavoriteProductResult;
import com.dressing.dressingproject.ui.models.FavoriteResult;
import com.dressing.dressingproject.ui.models.FitDeleteResult;
import com.dressing.dressingproject.ui.models.FitModel;
import com.dressing.dressingproject.ui.models.FitResult;
import com.dressing.dressingproject.ui.models.FittingListResult;
import com.dressing.dressingproject.ui.models.LocalAreaInfo;
import com.dressing.dressingproject.ui.models.LocalInfoResult;
import com.dressing.dressingproject.ui.models.LoginInfo;
import com.dressing.dressingproject.ui.models.MallResult;
import com.dressing.dressingproject.ui.models.PostEstimationResult;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.models.ProductResult;
import com.dressing.dressingproject.ui.models.ProductSearchResult;
import com.dressing.dressingproject.ui.models.RecommendCodiResult;
import com.dressing.dressingproject.ui.models.SearchItem;
import com.dressing.dressingproject.ui.models.SignInResult;
import com.dressing.dressingproject.ui.models.SignUpResult;
import com.dressing.dressingproject.ui.models.SucessResult;
import com.dressing.dressingproject.ui.models.UserItem;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;


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

        gson = new Gson();

        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            MySSLSocketFactory socketFactory = new MySSLSocketFactory(trustStore);
            socketFactory.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client = new AsyncHttpClient();
            client.setSSLSocketFactory(socketFactory);
            client.setCookieStore(new PersistentCookieStore(ApplicationLoader.getContext()));
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }


        client.setCookieStore(new PersistentCookieStore(ApplicationLoader.getContext()));
    }

    public HttpClient getHttpClient() {
        return client.getHttpClient();
    }


    public interface OnResultListener<T> {
        public void onSuccess(T result);
        public void onFail(int code);
    }

    //TODO: 지역검색
    private static final String LOCATION_INFO = "https://apis.skplanetx.com/tmap/poi/areas";

    public void requestGetLocalInfo(Context context, int param1, String param2, int param3, final OnResultListener<LocalAreaInfo> listener) {
        RequestParams params = new RequestParams();
        params.put("version", param1);
        if(param2 != null) {
            params.put("searchFlag", param2);
        }
        if(param2.equals("M")) {
            params.put("areaLLCode", param3);
        }

        Header[] headers = new Header[2];
        headers[0] = new BasicHeader("Accept", "application/json");
        headers[1] = new BasicHeader("appKey", "d77addcb-9e1a-3f32-8251-1e311f7adf31");

        client.get(context, LOCATION_INFO, headers, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, String responseString, Throwable throwable) {
                //Log.d("NetworkManager", "get local info Fail: " + statusCode + responseString);
                listener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString) {
                //Log.d("NetworkManager", "get local info Success" + responseString);
                LocalInfoResult result = gson.fromJson(responseString, LocalInfoResult.class);
                listener.onSuccess(result.localAreaInfo);
            }
        });
    }

    //서버주소
    private static final String SERVER = "http://54.64.106.31";

    //TODO:로그인 요청
    private static final String SIGNIN_URL = SERVER + "/member/login";

    public void requestPostSignin(final Context context, LoginInfo loginInfo, final OnResultListener<SignInResult> onResultListener) {
        RequestParams params = new RequestParams();
        params.put("email", loginInfo.getUserId());
        params.put("password", loginInfo.getPassword());


        client.post(context, SIGNIN_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    SignInResult signInResult = gson.fromJson(responseString, SignInResult.class);
                    onResultListener.onSuccess(signInResult);
                }catch (JsonSyntaxException e)
                {
                    Toast.makeText(context, "네트워크 상태가 불안정 합니다!", Toast.LENGTH_SHORT).show();
                }
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

    public void requestPostSignUp(Context context,LoginInfo loginInfo, final OnResultListener<SignUpResult> onResultListener) {
        RequestParams params = new RequestParams();
        params.put("Email", loginInfo.getUserId());
        params.put("Nickname", loginInfo.getNickName());
        params.put("Password", loginInfo.getPassword());
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
//                PostEstimationResult codiScoreResult = gson.fromJson(responseString, PostEstimationResult.class);
//                int code = estimationResult.code;
//                String msg = estimationResult.msg;
//                ArrayList<CoordinationItem> coordinationItems = estimationResult.coordinationList;
                //네이버 무비즈 객체와 같이 result 객체를 만들어 결과상태와 결과값 객체를 가지는 놈!
                Toast.makeText(context, codiModel.getTitle() + " 평가되었습니다!", Toast.LENGTH_SHORT).show();
                PostEstimationResult postEstimationResult = gson.fromJson(responseString, PostEstimationResult.class);
                postEstimationResult.setRating(ratting);

                onResultListener.onSuccess(postEstimationResult);
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

    public void requestGetRecommendCodi(Context context, int startIndex, int display, final OnResultListener<RecommendCodiResult> onResultListener) {
        RequestParams params = new RequestParams();
        params.add("start", String.valueOf(startIndex));
        params.add("display", String.valueOf(display));
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


    //TODO:연관상품 요청
    private static final String DETAIL_CODI_URL = SERVER + "/item";

    public void requestGetDetailCodi(final Context context,CodiModel codiModel, final OnResultListener<ProductResult> onResultListener)
    {
        RequestParams params = new RequestParams();
        params.put("coordinationNum", Integer.parseInt(codiModel.getCodiNum()));

        client.get(context,DETAIL_CODI_URL,params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ProductResult productResult = gson.fromJson(responseString, ProductResult.class);
                if(productResult.code != 400)
                {
                    onResultListener.onSuccess(productResult);
                }
                else
                {
                    Toast.makeText(context, "연관상품 요청 실패! \n 네트워크 연결을 확인해 주세요!", Toast.LENGTH_SHORT).show();
                }

//                int code = productResult.code;
//                String msg = productResult.msg;

            }
        });
    }

    //TODO:코디, 상품 찜(Favorite)요청
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

    private static final String DELETE_FAVORITE_URL = SERVER + "/selected/%s/%s";

    /**
     *
     * @param context
     * @param isFavoriteRequest //찜하기에서 오는 요청을 구분하는 Flag
     * @param productModel
     * @param codiModel
     * @param onResultListener
     */
    public void requestDeleteFavorite(final Context context,boolean isFavoriteRequest,ProductModel productModel,CodiModel codiModel, final OnResultListener<SucessResult> onResultListener) {

        String key ="";
        String num = "";

        //찜하기에서 오는 요청은 SelectedNum 을 사용!
        if(isFavoriteRequest)
        {
            if(productModel != null)
            {
                num =Integer.toString(productModel.getSelectedNum());

            }
            else
            {
                num =Integer.toString(codiModel.getSelectedNum());
            }
            key = "selectedNum";
        }
        else
        {
            if(productModel != null)
            {
                num =productModel.getProductNum();
                key = "itemNum";
            }
            else
            {
                num =codiModel.getCodiNum();
                key = "coordinationNum";
            }
        }


        String url = String.format(DELETE_FAVORITE_URL,key,num);

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

    //TODO:쇼핑몰 요청
    private static final String MALL_URL = SERVER + "/mall";

    public void requestGetShoppingMall(Context context,String area, String detailArea, final OnResultListener<MallResult> onResultListener) {
        final RequestParams params = new RequestParams();
        params.put("area", area);
        params.put("detailArea", detailArea);
        client.get(context, MALL_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                MallResult mallResult = gson.fromJson(responseString, MallResult.class);
                onResultListener.onSuccess(mallResult);
            }
        });
    }
    //TODO:브랜드 리스트 요청
    private static final String BRANDLIST_URL = SERVER + "/brandList";

    public void requestGetBrandList(Context context, final OnResultListener<BrandResult> onResultListener) {
        client.get(context, BRANDLIST_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                BrandResult brandResult= gson.fromJson(responseString, BrandResult.class);
                onResultListener.onSuccess(brandResult);
            }
        });
    }


    //TODO:연관코디보기
    private static final String DETAIL_PRODUCT_URL = SERVER + "/coordination";

    public void getNetworkDetailProduct(final Context context,ProductModel productModel, final OnResultListener<CodiResult> productItemsOnResultListener) {
        RequestParams params = new RequestParams();
        params.add("itemNum",productModel.getProductNum());

        client.get(context, DETAIL_PRODUCT_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                productItemsOnResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                CodiResult codiResult = gson.fromJson(responseString, CodiResult.class);
                productItemsOnResultListener.onSuccess(codiResult);
            }
        });
    }


    //찜코디 리스트 요청
    //추천코디요청
    //TODO:찜코디 요청
    private static final String FAVORITE_CODI_URL = SERVER + "/selected/coordination";
//    private static final String FAVORITE_CODI_URL = "http://demo3840985.mockable.io" + "/selected/coordination";

    public void requestGetFavoriteCodi(final Context context, final OnResultListener<FavoriteCodiResult> productItemsOnResultListener) {
        RequestParams params = new RequestParams();

        client.get(context, FAVORITE_CODI_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                productItemsOnResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                FavoriteCodiResult favoriteCodiResult = gson.fromJson(responseString, FavoriteCodiResult.class);
                productItemsOnResultListener.onSuccess(favoriteCodiResult);
            }
        });
    }

    //TODO:찜상품 리스트 요청
    private static final String FAVORITE_PODUCT_URL = SERVER + "/selected/item";

    public void requestGetFavoriteProduct(Context context, final OnResultListener<FavoriteProductResult> productItemsOnResultListener) {
        RequestParams params = new RequestParams();

        client.get(context, FAVORITE_PODUCT_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                productItemsOnResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                FavoriteProductResult favoriteCodiResult = gson.fromJson(responseString, FavoriteProductResult.class);
                productItemsOnResultListener.onSuccess(favoriteCodiResult);
            }
        });
    }

    //TODO:상품검색 요청
    private static final String PRODUCT_SEARCH_URL = SERVER + "/search";

    public void requestGetSearchProduct(Context context,SearchItem item, final OnResultListener<ProductSearchResult> productSearchItemsOnResultListener) {
        RequestParams params = new RequestParams();
        params.add("brandNum", item.brandNum);
        params.add("color",item.color);
        params.add("priceStart",item.priceStart);
        params.add("priceEnd", item.priceEnd);
        params.add("categoryNum",item.brandNum);
        params.add("categoryDetailNum",item.categoryDetailNum);
        params.add("start", String.valueOf(item.start));
        params.add("display", String.valueOf(item.display));


        client.get(context, PRODUCT_SEARCH_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                productSearchItemsOnResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ProductSearchResult productSearchResult = gson.fromJson(responseString, ProductSearchResult.class);
                productSearchItemsOnResultListener.onSuccess(productSearchResult);
            }
        });
    }


    //http://54.64.106.31/search?brandNum=&color&p riceStart&priceEnd&categoryNum&categoryDetailNum=


    //TODO:코디 fit 요청
    private static final String FIT_URL = SERVER + "/fitting";

    public void requestGetFitCodi(Context context,final CodiModel codiModel, final OnResultListener<FitResult> onResultListener) {
        RequestParams params = new RequestParams();
        params.add("coordinationNum",codiModel.getCodiNum());
        //params.add("selectedNum",Integer.toString(codiModel.getSelectedNum()));

        client.post(context, FIT_URL,params, new TextHttpResponseHandler() {
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

    //TODO:상품 fit 요청
    public void requestGetFitProduct(Context context,final ProductModel productModel,final OnResultListener<FitResult> onResultListener)
    {
        RequestParams params = new RequestParams();
        params.add("itemNum",productModel.getProductNum());
        //params.add("selectedNum",Integer.toString(productModel.getSelectedNum()));

        client.post(context, FIT_URL,params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                FitResult fitProductResult = new FitResult();
                fitProductResult.setFit(productModel.isFit());
                onResultListener.onSuccess(fitProductResult);
            }
        });
    }

    //TODO:피팅리스트 요청
    private static final String FITTINGLIST_URL = SERVER + "/fitting";

    public void requestGetFitting(Context context, int startIndex, int display, final OnResultListener<FittingListResult> OnResultListener) {
        RequestParams params = new RequestParams();
        params.add("start", String.valueOf(startIndex));
        params.add("display", String.valueOf(display));
        client.get(context, FITTINGLIST_URL,params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                OnResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                FittingListResult fittingListResult = gson.fromJson(responseString, FittingListResult.class);
                OnResultListener.onSuccess(fittingListResult);
            }
        });
    }

    //TODO:취향분석 요청
    private static final String ANALYSIS_URL = SERVER + "/analysis";

    public void requestAnalysis(Context context, final OnResultListener<AnalysisResult> OnResultListener) {
        client.get(context, ANALYSIS_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                OnResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                AnalysisResult analysisResult = gson.fromJson(responseString, AnalysisResult.class);
                OnResultListener.onSuccess(analysisResult);
            }
        });
    }

    //TODO:Fit delete

    private static final String DELETE_FIT_URL = SERVER + "/fitting/%s/%d";

    public void requestDeleteFit(final Context context, FitModel fitModel , final OnResultListener<FitDeleteResult> onResultListener) {
        String url ="";
        switch (fitModel.getFlag())
        {
            case FitModel.FIT_CODI:
                url = String.format(DELETE_FIT_URL,"coordinationNum",fitModel.fittingNum);
                break;
            case FitModel.FIT_PRODUCT:
                url = String.format(DELETE_FIT_URL,"itemNum",fitModel.fittingNum);
                break;
            case FitModel.FIT_FITTING:
                url = String.format(DELETE_FIT_URL,"fittingNum",fitModel.fittingNum);
                break;
        }


        client.delete(context, url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {


                FitDeleteResult fitDeleteResult = gson.fromJson(responseString, FitDeleteResult.class);
                onResultListener.onSuccess(fitDeleteResult);

            }
        });
    }

    public void cancelAll(Context context) {
        client.cancelRequests(context, true);
    }
}

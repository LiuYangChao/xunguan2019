package com.mibo.xunguan2019.module_login.net;

import com.mibo.xunguan2019.module_content.model.ContentModel;
import com.mibo.xunguan2019.module_home.db.entity.NodeEntity;
import com.mibo.xunguan2019.module_home.db.entity.TaskEntity;
import com.mibo.xunguan2019.module_home.db.entity.TemEntity;
import com.mibo.xunguan2019.module_home.model.NodeModel;
import com.mibo.xunguan2019.module_login.db.entity.PostEntity;
import com.mibo.xunguan2019.module_login.db.entity.UserEntity;
import com.mibo.xunguan2019.module_login.model.ValidateModel;
import com.mibo.xunguan2019.net_rxjava.config.BaseEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 登录模块网络请求
 * Author liuyangchao
 * Date on 2019/5/11.10:04
 */

public interface HttpService_Login {

    @GET("api/validateUser.rdm")
    Observable<BaseEntity<ValidateModel>> validateUser(@Query("username") String username, @Query("password") String password);

    @GET("api/getUserList.rdm")
    Observable<BaseEntity<List<UserEntity>>> getUserList();

    @GET("api/getPostList.rdm")
    Observable<BaseEntity<List<PostEntity>>> getPostList();

    @GET("api/getTaskListByUserName.rdm")
    Observable<BaseEntity<List<TaskEntity>>> getTaskListByUserName(@Query("userName") String userName);

    @GET("api/getNodeByTaskId.rdm")
    Observable<BaseEntity<List<NodeModel>>> getNodeByTaskId(@Query("taskId") String taskId);

    @GET("api/getTemplateListByNodeId.rdm")
    Observable<BaseEntity<List<TemEntity>>> getTemplateListByNodeId(@Query("nodeId") String nodeId);

    @GET("api/getContentById.rdm")
    Observable<BaseEntity<ContentModel>> getContentByTId(@Query("instanceId") String instanceId);

    @POST("api/uploadContent.rdm")
    Observable<BaseEntity<String>> uploadContent(@Body ContentModel contentModel);

    @Multipart
    @POST("UploadServlet")
    Observable<ResponseBody> uploadMapFile(@PartMap Map<String, RequestBody> params);

    //使用Streaming 方式 Retrofit 不会一次性将ResponseBody 读取进入内存，否则文件很多容易OOM
    @GET
    @Streaming
    Flowable<ResponseBody> download2(@Url String url);  //返回值使用 ResponseBody 之后会对ResponseBody 进行读取

}

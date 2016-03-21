package com.bzh.data.repository.network;

import com.bzh.data.ApplicationTestCase;
import com.bzh.data.film.entity.FilmDetailEntity;
import com.bzh.data.film.entity.FilmEntity;
import com.bzh.data.exception.DataLayerException;
import com.bzh.data.film.datasource.FilmNetWorkDataStore;
import com.bzh.data.service.RetrofitManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-13<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
@RunWith(MockitoJUnitRunner.class)
public class FilmNetWorkDataStoreTest extends ApplicationTestCase {

//    FilmNetWorkDataStore realNetWorkDataStore;

    @Mock
    FilmNetWorkDataStore mockNetWorkDataStore;

    @Mock
    DataLayerException dataLayerException;

    @Mock
    ArrayList<FilmEntity> filmEntities;

    @Mock
    FilmEntity filmEntity;

    @Before
    public void setUp() {
//        realNetWorkDataStore = new FilmNetWorkDataStore(RetrofitManager.getInstance());
    }

    @Test
    public void testGetNewest() throws Exception {
        when(mockNetWorkDataStore.getNewest(1)).thenReturn(Observable.create(new Observable.OnSubscribe<ArrayList<FilmEntity>>() {
            @Override
            public void call(Subscriber<? super ArrayList<FilmEntity>> subscriber) {
                subscriber.onError(dataLayerException);
            }
        }));
        when(dataLayerException.getCode()).thenReturn(DataLayerException.ERROR_NONE_NETWORK);
        when(dataLayerException.getMessage()).thenReturn(DataLayerException.LABEL_NONE_NETWORK);
        mockNetWorkDataStore.getNewest(1).subscribe(new Subscriber<ArrayList<FilmEntity>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                assertNotNull(e);
                assertTrue(e instanceof DataLayerException);
                DataLayerException exception = (DataLayerException) e;
                assertEquals(exception.getMessage(), DataLayerException.LABEL_NONE_NETWORK);
                assertEquals(exception.getCode(), DataLayerException.ERROR_NONE_NETWORK);
            }

            @Override
            public void onNext(ArrayList<FilmEntity> filmEntities) {
            }
        });


        when(mockNetWorkDataStore.getNewest(131)).thenReturn(Observable.create(new Observable.OnSubscribe<ArrayList<FilmEntity>>() {
            @Override
            public void call(Subscriber<? super ArrayList<FilmEntity>> subscriber) {
                subscriber.onNext(filmEntities);
                subscriber.onCompleted();
            }
        }));

        when(filmEntities.get(0)).thenReturn(filmEntity);
        when(filmEntities.get(0).getName()).thenReturn("道士下山");
        when(filmEntities.get(0).getUrl()).thenReturn("http://www.baidu.com");
        mockNetWorkDataStore.getNewest(131).subscribe(new Subscriber<ArrayList<FilmEntity>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ArrayList<FilmEntity> filmEntities) {
                assertNotNull(filmEntities);
                assertEquals(filmEntities.get(0).getName(), "道士下山");
                assertEquals(filmEntities.get(0).getUrl(), "http://www.baidu.com");
            }
        });
    }

//    @Deprecated
//    @Test
//    public void testGetNewestReal() {
//        // 真实数据
//        realNetWorkDataStore.getNewest(1).subscribe(new Subscriber<FilmEntity>() {
//            @Override
//            public void onCompleted() {
//                System.out.println("FilmNetWorkDataStoreTest.onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("e = [" + e + "]");
//            }
//
//            @Override
//            public void onNext(FilmEntity s) {
//                assertNotNull(s);
//                System.out.println("s = [" + s + "]");
//            }
//        });
//    }

//    @Deprecated
//    @Test
//    public void testGetFilmDetail() throws Exception {
//        realNetWorkDataStore.getFilmDetail("/html/gndy/dyzz/20160309/50431.html")
//                .subscribe(new Subscriber<FilmDetailEntity>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(FilmDetailEntity s) {
//                        assertNotNull(s);
//                        System.out.println("FilmDetailEntity = [" + s.toString() + "]");
//                    }
//                });
//    }

//    @Deprecated
//    @Test
//    public void testGetFilmDetailList() {
//        final long start = System.currentTimeMillis();
//        realNetWorkDataStore.getNewest(1)
//                .flatMap(new Func1<FilmEntity, Observable<FilmDetailEntity>>() {
//                    @Override
//                    public Observable<FilmDetailEntity> call(FilmEntity s) {
//                        return realNetWorkDataStore.getFilmDetail(s.getUrl());
//                    }
//                })
//                .subscribe(new Subscriber<FilmDetailEntity>() {
//                    @Override
//                    public void onCompleted() {
//                        long end = System.currentTimeMillis();
//                        System.out.println("total time = [" + (end - start) / 1000 + "]");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(FilmDetailEntity filmDetailEntity) {
//                        assertNotNull(filmDetailEntity);
//                        assertNotNull(filmDetailEntity.getTitle());
//                        System.out.println("filmDetailEntity = [" + filmDetailEntity.getTitle() + "]");
//                    }
//                });
//    }

}
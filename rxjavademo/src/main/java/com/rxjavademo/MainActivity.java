package com.rxjavademo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends Activity {

    private Button btn_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("info", Thread.currentThread().getName() + "  a()");
                a();
            }
        });
        findViewById(R.id.btn_print).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print();
            }
        });
        findViewById(R.id.btn_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapChange();
            }
        });
        findViewById(R.id.btn_from).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from();
            }
        });
        findViewById(R.id.btn_flatMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flatMap();
            }
        });
        btn_click = (Button)findViewById(R.id.btn_notMoreClick);
        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notClickMore();
            }
        });
    }

    private void notClickMore() {
        RxView.clicks(btn_click)
                .throttleFirst(3, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Toast.makeText(MainActivity.this, "3秒内只允许点击一次", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //Observable.flatMap()
    private void flatMap() {
        Observable<List<String>> myObservableStr = Observable.create(
                new Observable.OnSubscribe<List<String>>() {
                    @Override
                    public void call(Subscriber<? super List<String>> subscriber) {
                        List<String> list = new ArrayList<>();
                        list.add("str1");
                        list.add("str2");
                        list.add("str3");
                        subscriber.onNext(list);
                    }
                }
        );
        /**
         * Observable.flatMap()接收一个Observable的输出作为输入，同时输出另外一个Observable
         */
  /*      myObservableStr.flatMap(new Func1<List<String>, Observable<String>>() {
            @Override
            public Observable<String> call(List<String> strs) {
                return Observable.from(strs);
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i("info", s + "");
            }
        });
        */
        myObservableStr.flatMap(new Func1<List<String>, Observable<String>>() {
            @Override
            public Observable<String> call(final List<String> strings) {
                return Observable.from(strings);
            }
        }).flatMap(new Func1<String, Observable<?>>() {
            @Override
            public Observable<?> call(final String s) {
                //返回值不是一个String，而是一个输出String的Observabl对象：
                return Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("title: " + s);
                    }
                });
            }
        })
                /**
                 *  filter()输出和输入相同的元素，并且会过滤掉那些不满足检查条件的
                 */

                .filter(new Func1<Object, Boolean>() {
                    @Override
                    public Boolean call(Object o) {
                        return !o.equals("title: str2");//输出条件
                    }
                })
                /**
                 * take()输出最多指定数量的结果。
                 */
                .take(1)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Log.i("info", "myObservableUrls.flatMap onNext o: " + o);
                    }
                });

    }

    //Observable.from()方法，它接收一个集合作为输入，然后每次输出一个元素给subscriber：
    private void from() {
        List<String> list = new ArrayList<>();
        list.add("str1");
        list.add("str2");
        list.add("str3");

        Observable.from(list).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i("info", s + "");
            }
        });
    }

    private void print() {
       /* //创建一个Observable对象很简单，直接调用Observable.create即可
        Observable<String> observable = Observable.create(
                new Observable.OnSubscribe<String>() {

                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("Hello,World");
                        subscriber.onCompleted();
                    }
                }
        );
        //创建一个Subscriber来处理Observable对象发出的字符串。
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Toast.makeText(MainActivity.this, s + "", Toast.LENGTH_SHORT).show();
            }
        };
        //subscriber对observable的订阅
        observable.subscribe(subscriber);*/
        /**
         * 简化上面的代码
         */
        Observable.just("Hello,World").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Toast.makeText(MainActivity.this, s + "", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //map变换
    void mapChange() {
        //map 变换
 /*       Observable.just("Hello world").map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s + " -david";
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Toast.makeText(MainActivity.this, s + "", Toast.LENGTH_SHORT).show();
            }
        });*/
        //map变换，变换类型
        Subscription subscription =  Observable.just("123").map(new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                return Integer.parseInt(s) + 2;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer s) {
                Toast.makeText(MainActivity.this, s + "", Toast.LENGTH_SHORT).show();
            }
        });
        //subscription.isUnsubscribed();//这个Subscription对象来操作被观察者和订阅者之间的联系.
    }


    //线程切换Scheduler
    void a() {
        Observable.just(1, 2, 3, 4)//IO线程，由subScribeOn()指定
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        Log.e("info", Thread.currentThread().getName() + "  Schedulers.newThread()");
                        return "call" + integer;
                    }
                })//新线程，由observeOn（）指定
                .observeOn(Schedulers.io())
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        Log.i("info", Thread.currentThread().getName() + " Schedulers.io()");
                        final char[] chars = s.toCharArray();
                        int res = -1;
                        for (char c : chars) {
                            res += c;
                        }
                        return res;
                    }
                })//指定 Subsctiber 的回调发生在主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.d("info", "Thread:" + Thread.currentThread().toString() + "->onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("info", "Thread:" + Thread.currentThread().toString() + "->onError:" + e.toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("info", "Thread:" + Thread.currentThread().toString() + "->onNext:" + integer);
                    }
                });
    }
}

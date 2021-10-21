package com.jwandroid.android_global_kit.application.rx;

import android.annotation.SuppressLint;
import android.util.SparseArray;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.lang.annotation.Retention;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class RxBus {

    private static SparseArray<PublishSubject<Object>> sSubjectMap = new SparseArray<>();
    private static Map<Object, CompositeDisposable> sSubscriptionsMap = new HashMap<>();

    public static final int EVENT = 100000;

    @Retention(SOURCE)
    @IntDef({
            EVENT
    })
    @interface Subject {

    }

    private RxBus() {
        // hidden constructor
    }
    /**
     * Get the subject or create it if it's not already in memory.
     */
    @SuppressLint("CheckResult")
    @NonNull
    public static PublishSubject<Object> getSubject(@Subject int subjectKey) {
        PublishSubject<Object> subject = sSubjectMap.get(subjectKey);
        if (subject == null) {
            subject = PublishSubject.create();
            subject.subscribeOn(AndroidSchedulers.mainThread());
            sSubjectMap.put(subjectKey, subject);
        }

        return subject;
    }

    /**
     * Get the CompositeDisposable or create it if it's not already in memory.
     */
    @NonNull
    private static CompositeDisposable getCompositeDisposable(@NonNull Object object) {
        CompositeDisposable compositeDisposable = sSubscriptionsMap.get(object);
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
            sSubscriptionsMap.put(object, compositeDisposable);
        }

        return compositeDisposable;
    }

    /**
     * Subscribe to the specified subject and listen for updates on that subject. Pass in an object to associate
     * your registration with, so that you can unsubscribe later.
     * <br/><br/>
     * <b>Note:</b> Make sure to call {@link RxBus#unregister(Object)} to avoid memory leaks.
     */
    public static void subscribe(@Subject int subject, @NonNull Object lifecycle, @NonNull Consumer<Object> action) {
        Disposable disposable = getSubject(subject)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action);
        getCompositeDisposable(lifecycle).add(disposable);
    }

    public static void subscribe (@Subject int subject, @NonNull Object lifecycle,
                                  @NonNull Function<Object, Object> mapper , @NonNull Consumer<Object> action) {
        Disposable disposable = getSubject(subject)
                .observeOn(Schedulers.io())
                .map(mapper)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action);
        getCompositeDisposable(lifecycle).add(disposable);
    }

    public static void subscribeOnMainThread(@Subject int subject, @NonNull Object lifecycle, @NonNull Consumer<Object> action) {
        Disposable disposable = getSubject(subject)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action);
        getCompositeDisposable(lifecycle).add(disposable);
    }

    /**
     * Unregisters this object from the bus, removing all subscriptions.
     * This should be called when the object is going to go out of memory.
     */
    public static void unregister(@NonNull Object lifecycle) {
        //We have to remove the composition from the map, because once you dispose it can't be used anymore
        CompositeDisposable compositeDisposable = sSubscriptionsMap.remove(lifecycle);
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    /**
     * Publish an object to the specified subject for all subscribers of that subject.
     */
    public static void publish(@Subject int subject, @NonNull Object message) {
        getSubject(subject).onNext(message);
    }

}

package com.cyber.accounting.movies.app.domain.interactors;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Yasser.Ibrahim on 6/27/2018.
 */

public abstract class BaseInteractor {
    protected final CompositeDisposable disposables = new CompositeDisposable();

    protected synchronized void prepare(Observable observable, BaseObserver observer) {
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void onDestroy() {
        if (disposables != null && !disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    public abstract class BaseObserver<T> implements Observer<T> {
        private MainInteractor.CallbackStates callback;

        public BaseObserver(MainInteractor.CallbackStates callback) {
            this.callback = callback;
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            disposables.add(disposable);
        }

        @Override
        public void onNext(T t) {
            callback.hideProgress();
        }

        @Override
        public void onError(Throwable e) {
            callback.hideProgress();
        }

        @Override
        public void onComplete() {
            callback = null;
        }
    }
}

package gupuru.kotlinretrofitsample.extention

import rx.Observable
import rx.Subscriber
import rx.Subscription

public fun <T> Observable<T>.onError(block : (Throwable) -> Unit): KSubscription<T> {
    return KSubscription(this).onError(block)
}

public fun <T> Observable<T>.onCompleted(block : () -> Unit): KSubscription<T> {
    return KSubscription(this).onCompleted(block)
}

public fun <T> Observable<T>.onNext(block : (T) -> Unit): KSubscription<T> {
    return KSubscription(this).onNext(block)
}

public fun Subscription.onError(block: (Throwable) -> Unit): Subscription {
    return this
}

public class KSubscription<T>(val observable: Observable<T>) {

    private var error: (Throwable) -> Unit = { throw it }
    private var completed: () -> Unit = {}
    private var next: (T) -> Unit = {}

    fun onError(block: (Throwable) -> Unit): KSubscription<T> {
        error = block
        return this
    }

    fun onCompleted(block: () -> Unit): KSubscription<T> {
        completed = block
        return this
    }

    fun onNext(block: (T) -> Unit): KSubscription<T> {
        next = block
        return this
    }

    fun subscribe(): Subscription = observable.subscribe(object : Subscriber<T>(){
        override fun onError(e: Throwable?) {
            if ( e == null ) {
                return
            }

            error.invoke(e)
        }

        override fun onCompleted() {
            completed.invoke()
        }

        override fun onNext(t: T) {
            next.invoke(t)
        }
    })

}
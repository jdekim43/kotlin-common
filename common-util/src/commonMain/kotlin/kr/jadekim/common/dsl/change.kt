package kr.jadekim.common.dsl

class ChangeContext<T>(val before: T, val after: T) {

    inline fun T.to(to: T, crossinline block: () -> Unit) {
        if (this@ChangeContext.before == this && this@ChangeContext.after == to) {
            block()
        }
    }
}

@Deprecated("Deprecated this function", ReplaceWith("matchChanges"))
inline fun <T> change(before: T, after: T, crossinline block: ChangeContext<T>.() -> Unit) {
    ChangeContext(before, after).block()
}

inline fun <T> matchChanges(before: T, after: T, crossinline block: ChangeContext<T>.() -> Unit) {
    ChangeContext(before, after).block()
}

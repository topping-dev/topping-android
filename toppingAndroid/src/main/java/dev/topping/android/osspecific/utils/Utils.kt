package dev.topping.android.osspecific.utils

import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import java.io.Serializable

fun <V> Map<String, V>.toBundle(bundle: Bundle = Bundle()): Bundle = bundle.apply {
    forEach {
        val k = it.key
        val v = it.value
        when (v) {
            is IBinder -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                putBinder(k, v)
            }
            is Bundle -> putBundle(k, v)
            is Byte -> putByte(k, v)
            is ByteArray -> putByteArray(k, v)
            is Char -> putChar(k, v)
            is CharArray -> putCharArray(k, v)
            is CharSequence -> putCharSequence(k, v)
            is Float -> putFloat(k, v)
            is FloatArray -> putFloatArray(k, v)
            is Parcelable -> putParcelable(k, v)
            is Serializable -> putSerializable(k, v)
            is Short -> putShort(k, v)
            is ShortArray -> putShortArray(k, v)

//      is Size -> putSize(k, v) //api 21
//      is SizeF -> putSizeF(k, v) //api 21

            else -> throw IllegalArgumentException("$v is of a type that is not currently supported")
//      is Array<*> -> TODO()
//      is List<*> -> TODO()
        }
    }
}

class Utils {
}
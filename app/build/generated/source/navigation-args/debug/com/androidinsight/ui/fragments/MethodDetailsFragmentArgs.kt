package com.androidinsight.ui.fragments

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Long
import kotlin.jvm.JvmStatic

public data class MethodDetailsFragmentArgs(
  public val methodId: Long,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putLong("methodId", this.methodId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("methodId", this.methodId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): MethodDetailsFragmentArgs {
      bundle.setClassLoader(MethodDetailsFragmentArgs::class.java.classLoader)
      val __methodId : Long
      if (bundle.containsKey("methodId")) {
        __methodId = bundle.getLong("methodId")
      } else {
        throw IllegalArgumentException("Required argument \"methodId\" is missing and does not have an android:defaultValue")
      }
      return MethodDetailsFragmentArgs(__methodId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): MethodDetailsFragmentArgs {
      val __methodId : Long?
      if (savedStateHandle.contains("methodId")) {
        __methodId = savedStateHandle["methodId"]
        if (__methodId == null) {
          throw IllegalArgumentException("Argument \"methodId\" of type long does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"methodId\" is missing and does not have an android:defaultValue")
      }
      return MethodDetailsFragmentArgs(__methodId)
    }
  }
}

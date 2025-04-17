package com.androidinsight.ui.fragments

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Long
import kotlin.String
import kotlin.jvm.JvmStatic

public data class ComponentDetailsFragmentArgs(
  public val componentId: Long,
  public val componentType: String,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putLong("componentId", this.componentId)
    result.putString("componentType", this.componentType)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("componentId", this.componentId)
    result.set("componentType", this.componentType)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): ComponentDetailsFragmentArgs {
      bundle.setClassLoader(ComponentDetailsFragmentArgs::class.java.classLoader)
      val __componentId : Long
      if (bundle.containsKey("componentId")) {
        __componentId = bundle.getLong("componentId")
      } else {
        throw IllegalArgumentException("Required argument \"componentId\" is missing and does not have an android:defaultValue")
      }
      val __componentType : String?
      if (bundle.containsKey("componentType")) {
        __componentType = bundle.getString("componentType")
        if (__componentType == null) {
          throw IllegalArgumentException("Argument \"componentType\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"componentType\" is missing and does not have an android:defaultValue")
      }
      return ComponentDetailsFragmentArgs(__componentId, __componentType)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle):
        ComponentDetailsFragmentArgs {
      val __componentId : Long?
      if (savedStateHandle.contains("componentId")) {
        __componentId = savedStateHandle["componentId"]
        if (__componentId == null) {
          throw IllegalArgumentException("Argument \"componentId\" of type long does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"componentId\" is missing and does not have an android:defaultValue")
      }
      val __componentType : String?
      if (savedStateHandle.contains("componentType")) {
        __componentType = savedStateHandle["componentType"]
        if (__componentType == null) {
          throw IllegalArgumentException("Argument \"componentType\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"componentType\" is missing and does not have an android:defaultValue")
      }
      return ComponentDetailsFragmentArgs(__componentId, __componentType)
    }
  }
}

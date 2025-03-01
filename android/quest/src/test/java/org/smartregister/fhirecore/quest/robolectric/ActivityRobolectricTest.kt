/*
 * Copyright 2021 Ona Systems, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.smartregister.fhirecore.quest.robolectric

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import org.junit.After

abstract class ActivityRobolectricTest : RobolectricTest() {

  @After
  fun testDown() {
    getActivity().finish()
  }

  abstract fun getActivity(): Activity

  fun getString(@StringRes id: Int): String {
    return getActivity().getString(id)
  }

  fun <T : View> findViewById(@IdRes id: Int): T {
    return getActivity().findViewById(id)
  }
}

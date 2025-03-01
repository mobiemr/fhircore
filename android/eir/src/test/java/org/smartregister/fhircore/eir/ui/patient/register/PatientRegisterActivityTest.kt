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

package org.smartregister.fhircore.eir.ui.patient.register

import android.app.Activity
import androidx.core.content.ContextCompat
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.robolectric.Robolectric
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.fakes.RoboMenuItem
import org.smartregister.fhircore.eir.R
import org.smartregister.fhircore.eir.activity.ActivityRobolectricTest
import org.smartregister.fhircore.eir.coroutine.CoroutineTestRule
import org.smartregister.fhircore.eir.shadow.EirApplicationShadow
import org.smartregister.fhircore.eir.shadow.FakeKeyStore
import org.smartregister.fhircore.eir.shadow.ShadowNpmPackageProvider
import org.smartregister.fhircore.engine.ui.register.model.SideMenuOption

@Config(shadows = [EirApplicationShadow::class, ShadowNpmPackageProvider::class])
class PatientRegisterActivityTest : ActivityRobolectricTest() {

  private lateinit var patientRegisterActivity: PatientRegisterActivity
  @get:Rule var coroutinesTestRule = CoroutineTestRule()

  @Before
  fun setUp() {
    patientRegisterActivity =
      Robolectric.buildActivity(PatientRegisterActivity::class.java).create().resume().get()
  }

  @Test
  fun testSideMenuOptionsShouldReturnCovaxMenuOptions() {
    val menu = patientRegisterActivity.sideMenuOptions()

    Assert.assertEquals(1, menu.size)
    with(menu.first()) {
      Assert.assertEquals(R.id.menu_item_covax, itemId)
      Assert.assertEquals(R.string.client_list_title_covax, titleResource)
      Assert.assertTrue(opensMainRegister)
      Assert.assertEquals(
        shadowOf(ContextCompat.getDrawable(patientRegisterActivity, R.drawable.ic_baby_mother))
          .createdFromResId,
        shadowOf(iconResource).createdFromResId
      )
    }
  }

  @Test
  fun testOnSideMenuOptionSelectedShouldReturnTrue() {
    Assert.assertTrue(patientRegisterActivity.onMenuOptionSelected(RoboMenuItem()))
  }

  @Test
  fun testUpdateCountShouldSetRightValue() = runBlockingTest {
    val spy = spyk(patientRegisterActivity)
    every { spy.findSideMenuItem(any()) } returns
      RoboMenuItem().apply { itemId = R.id.menu_item_covax }

    val sideMenuOption =
      SideMenuOption(R.id.menu_item_covax, R.string.covax_app, mockk(), countMethod = { 123 })
    sideMenuOption.count = 0

    spy.updateCount(sideMenuOption)

    Assert.assertEquals(123, sideMenuOption.count)
  }

  @Test
  fun testSupportedFragmentsShouldReturnPatientRegisterFragmentList() {
    val fragments = patientRegisterActivity.supportedFragments()

    Assert.assertEquals(1, fragments.size)
    Assert.assertEquals(
      PatientRegisterFragment::class.java.simpleName,
      fragments.first().javaClass.simpleName
    )
  }

  override fun getActivity(): Activity {
    return patientRegisterActivity
  }

  companion object {
    @JvmStatic
    @BeforeClass
    fun beforeClass() {
      FakeKeyStore.setup
    }
  }
}

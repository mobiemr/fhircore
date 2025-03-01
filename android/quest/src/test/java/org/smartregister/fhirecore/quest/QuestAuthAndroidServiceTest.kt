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

package org.smartregister.fhirecore.quest

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.robolectric.Robolectric
import org.robolectric.annotation.Config
import org.smartregister.fhircore.quest.QuestAuthAndroidService
import org.smartregister.fhirecore.quest.robolectric.RobolectricTest
import org.smartregister.fhirecore.quest.shadow.QuestApplicationShadow

@Config(shadows = [QuestApplicationShadow::class])
class QuestAuthAndroidServiceTest : RobolectricTest() {

  private lateinit var accountService: QuestAuthAndroidService

  @Before
  fun setUp() {
    accountService =
      Robolectric.buildService(QuestAuthAndroidService::class.java, null).create().get()
  }

  @Test
  fun testOnCreateShouldCreateAccountAuthenticatorObject() {
    val field = accountService.javaClass.getDeclaredField("authenticator")
    field.isAccessible = true
    Assert.assertNotNull(field.get(accountService))
  }

  @Test
  fun testOnBindShouldReturnNonNullIBinder() {
    Assert.assertNotNull(accountService.onBind(null))
  }
}

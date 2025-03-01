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

package org.smartregister.fhirecore.quest.ui.patient.register

import java.util.Calendar
import java.util.Date
import org.hl7.fhir.r4.model.DateType
import org.hl7.fhir.r4.model.Patient
import org.hl7.fhir.r4.model.StringType
import org.junit.Assert.assertEquals
import org.junit.Test
import org.robolectric.annotation.Config
import org.smartregister.fhircore.quest.ui.patient.register.PatientItemMapper
import org.smartregister.fhirecore.quest.robolectric.RobolectricTest
import org.smartregister.fhirecore.quest.shadow.QuestApplicationShadow

@Config(shadows = [QuestApplicationShadow::class])
class PatientMapperTest : RobolectricTest() {

  @Test
  fun testMapToDomainModel() {
    val dto = buildPatient("123456", "Doe", "John", 12)
    val patientItem = PatientItemMapper.mapToDomainModel(dto)
    with(patientItem) {
      assertEquals("12", age)
      assertEquals("John Doe", name)
      assertEquals("123456", id)
      assertEquals("123456", identifier)
      assertEquals("Dist 1 City 1", address)
    }
  }

  private fun buildPatient(id: String, family: String, given: String, age: Int): Patient {
    return Patient().apply {
      this.id = id
      this.identifierFirstRep.value = id
      this.addName().apply {
        this.family = family
        this.given.add(StringType(given))
      }
      this.birthDate = DateType(Date()).apply { add(Calendar.YEAR, -age) }.dateTimeValue().value

      this.addAddress().apply {
        district = "Dist 1"
        city = "City 1"
      }
    }
  }
}

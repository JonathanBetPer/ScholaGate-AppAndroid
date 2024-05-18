package me.scholagate.app.dtos


import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

/**
 * Clase de pruebas unitarias para la clase AlumnoDto
 * @see AlumnoDto
 * @since 18/05/2024
 */
class AlumnoDtoTest {

    /**
     * Test para verificar que un alumno mayor de edad es mayor de edad
     */
    @Test
    fun isMayorDeEdadTrue() {
        val alumno = AlumnoDto()
        alumno.fechaNac = LocalDate.now().minusYears(18).toString()
        assertTrue(alumno.isMayorDeEdad())
    }

    /**
     * Test para verificar que un alumno menor de edad no es mayor de edad
     */
    @Test
    fun isMayorDeEdadFalse() {
        val alumno = AlumnoDto()
        alumno.fechaNac = LocalDate.now().toString()
        assertFalse(alumno.isMayorDeEdad())
    }
}
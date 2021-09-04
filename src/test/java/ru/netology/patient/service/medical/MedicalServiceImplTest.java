package ru.netology.patient.service.medical;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.alert.SendAlertServiceImpl;

import java.math.BigDecimal;

class MedicalServiceImplTest {

    @Test
    void checkBloodPressureTestText() {

        BloodPressure bloodPressureBroken = new BloodPressure(4,-1);
        BloodPressure bloodPressure = new BloodPressure( 2, 0);

        HealthInfo healthInfoMock = Mockito.mock(HealthInfo.class);
        // информация о здоровье возвращает плохое давление
        Mockito.when(healthInfoMock.getBloodPressure()).thenReturn(bloodPressureBroken);

        PatientInfo patientInfoMock = Mockito.mock(PatientInfo.class);
        // инфо о пациенте возвращает id="test",
        Mockito.when(patientInfoMock.getId()).thenReturn("test");
        // возвращает то инфо о здоровье, которое должно вернуть плохое давление
        Mockito.when(patientInfoMock.getHealthInfo()).thenReturn(healthInfoMock);

        PatientInfoRepository patientInfoRepositoryMock = Mockito.mock(PatientInfoFileRepository.class);
        // возвращает того самого пациента с плохим давлением
        Mockito.when(patientInfoRepositoryMock.getById("test")).thenReturn(patientInfoMock);

        // далее делаем перехват аргумента,
        SendAlertService sendAlertServiceMock = Mockito.mock(SendAlertServiceImpl.class);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepositoryMock, sendAlertServiceMock);
        medicalService.checkBloodPressure("test", bloodPressure);

        // хотим убедиться, что именно такую строку передает наш метод в "alertService.send(message);"
        Mockito.verify(sendAlertServiceMock).send(argumentCaptor.capture());
        // сообщение должно быть именно таким
        assertThat("Warning, patient with id: test, need help", is(argumentCaptor.getValue()));
    }

    @Test
    void checkBloodPressureTestNoSending() {

        BloodPressure bloodPressure = new BloodPressure( 2, 0);

        HealthInfo healthInfoMock = Mockito.mock(HealthInfo.class);
        // информация о здоровье возвращает искомое давление
        Mockito.when(healthInfoMock.getBloodPressure()).thenReturn(bloodPressure);

        PatientInfo patientInfoMock = Mockito.mock(PatientInfo.class);
        // инфо о пациенте возвращает id="test",
        Mockito.when(patientInfoMock.getId()).thenReturn("test");
        // возвращает то инфо о здоровье, которое должно вернуть искомое давление
        Mockito.when(patientInfoMock.getHealthInfo()).thenReturn(healthInfoMock);

        PatientInfoRepository patientInfoRepositoryMock = Mockito.mock(PatientInfoFileRepository.class);
        // возвращает того самого пациента с искомым давлением
        Mockito.when(patientInfoRepositoryMock.getById("test")).thenReturn(patientInfoMock);

        // далее делаем перехват аргумента,
        SendAlertService sendAlertServiceMock = Mockito.mock(SendAlertServiceImpl.class);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepositoryMock, sendAlertServiceMock);
        medicalService.checkBloodPressure("test", bloodPressure);

        // хотим убедиться, что "alertService.send(message);" не выполняется
        Mockito.verify(sendAlertServiceMock, Mockito.never()).send(Mockito.any());
    }

    @Test
    void checkTemperatureTestText() {

        BigDecimal badTemperature = new BigDecimal(22);
        BigDecimal goodTemperature = new BigDecimal(20);

        HealthInfo healthInfoMock = Mockito.mock(HealthInfo.class);
        // информация о здоровье возвращает плохую температуру
        Mockito.when(healthInfoMock.getNormalTemperature()).thenReturn(badTemperature);

        PatientInfo patientInfoMock = Mockito.mock(PatientInfo.class);
        // инфо о пациенте возвращает id="test",
        Mockito.when(patientInfoMock.getId()).thenReturn("test");
        // возвращает то инфо о здоровье, которое должно вернуть плохое температуру
        Mockito.when(patientInfoMock.getHealthInfo()).thenReturn(healthInfoMock);

        PatientInfoRepository patientInfoRepositoryMock = Mockito.mock(PatientInfoFileRepository.class);
        // возвращает того самого пациента с плохим температурой
        Mockito.when(patientInfoRepositoryMock.getById("test")).thenReturn(patientInfoMock);

        // далее делаем перехват аргумента,
        SendAlertService sendAlertServiceMock = Mockito.mock(SendAlertServiceImpl.class);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepositoryMock, sendAlertServiceMock);
        medicalService.checkTemperature("test", goodTemperature);

        // хотим убедиться, что именно такую строку передает наш метод в "alertService.send(message);"
        Mockito.verify(sendAlertServiceMock).send(argumentCaptor.capture());
        // сообщение должно быть именно таким
        assertThat("Warning, patient with id: test, need help", is(argumentCaptor.getValue()));

    }

    @Test
    void checkTemperatureTestNoSending() {

        BigDecimal goodTemperature = new BigDecimal(20);

        HealthInfo healthInfoMock = Mockito.mock(HealthInfo.class);
        // информация о здоровье возвращает хоорошую температуру
        Mockito.when(healthInfoMock.getNormalTemperature()).thenReturn(goodTemperature);

        PatientInfo patientInfoMock = Mockito.mock(PatientInfo.class);
        // инфо о пациенте возвращает id="test",
        Mockito.when(patientInfoMock.getId()).thenReturn("test");
        // возвращает то инфо о здоровье, которое должно вернуть хоорошую температуру
        Mockito.when(patientInfoMock.getHealthInfo()).thenReturn(healthInfoMock);

        PatientInfoRepository patientInfoRepositoryMock = Mockito.mock(PatientInfoFileRepository.class);
        // возвращает того самого пациента с хоорошую температурой
        Mockito.when(patientInfoRepositoryMock.getById("test")).thenReturn(patientInfoMock);

        // далее делаем перехват аргумента,
        SendAlertService sendAlertServiceMock = Mockito.mock(SendAlertServiceImpl.class);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepositoryMock, sendAlertServiceMock);
        medicalService.checkTemperature("test", goodTemperature);

        // хотим убедиться, что "alertService.send(message);" не выполняется
        Mockito.verify(sendAlertServiceMock, Mockito.never()).send(Mockito.any());

    }

}
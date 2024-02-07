package ru.slenergo.AppMonitoring.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.slenergo.AppMonitoring.model.DataVos5;
import ru.slenergo.AppMonitoring.repository.DataRepositoryVos15;
import ru.slenergo.AppMonitoring.repository.DataRepositoryVos5;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {
    @Autowired
    DataRepositoryVos5 dataRep5;
    @Autowired
    DataRepositoryVos15 dataRep15;

    /** Получить все данные
     * @return
     */
    public List<DataVos5> getAllVos5() {
        List<DataVos5> dataVos5 = new ArrayList<>();
        dataRep5.findAll().iterator().forEachRemaining(dataVos5::add);
        return dataVos5;
    }

    /** Получить данные за последние 31 день
     * @return
     */
    public  List<DataVos5> getLastMont(){
        List<DataVos5> dataVos5 = new ArrayList<>();
        dataRep5.findLastMont().iterator().forEachRemaining(dataVos5::add);
        return dataVos5;
    }

    /** Получить последнюю запись
     * @return
     */
    public DataVos5 getLastDataItem(){
        DataVos5 data = dataRep5.findLastItem();
        return data;
    }


}

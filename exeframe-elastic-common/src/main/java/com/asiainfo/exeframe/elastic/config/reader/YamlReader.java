package com.asiainfo.exeframe.elastic.config.reader;

import com.asiainfo.exeframe.elastic.config.ConfigReader;
import com.asiainfo.exeframe.elastic.config.ProcessType;
import com.asiainfo.exeframe.elastic.config.vm.VMDefinition;
import com.asiainfo.exeframe.elastic.config.vm.VMParam;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Set;

@Slf4j
public class YamlReader extends ConfigReader {


    public YamlReader(URL url) {
        super(url);
    }

    @Override
    public void loadConfig() {
        InputStream inputStream = null;
        try {
            inputStream = getUrl().openStream();
            Yaml yaml = new Yaml();
            Iterable<Object> all = yaml.loadAll(inputStream);
            for (Object item : all) {
                if (item instanceof Map) {
                    Map<String, Object> itemMap = (Map<String, Object>) item;
                    String whoami = (String) itemMap.get("whoami");
                    getConfigRegistry().setWhoami(whoami);
                    String defaultZkconnectString = (String) itemMap.get("defaultZkconnectString");
                    getConfigRegistry().setDefaultZkconnectString(defaultZkconnectString);
                    Map<String, Map<String, Object>> bgprocess = (Map<String, Map<String, Object>>) itemMap.get("process");
                    for (Map.Entry<String, Map<String, Object>> entry : bgprocess.entrySet()) {
                        ProcessType processType = ProcessType.toProcessType(entry.getKey());
                        Map<String, Object> val = entry.getValue();
                        switch (processType) {
                            case VM: {
                                Set<String> queueIds = val.keySet();
                                for (String queueId : queueIds) {
                                    VMDefinition vmDefinition = new VMDefinition();
                                    VMParam vmParam = new VMParam();
                                    vmParam.setQueueId(queueId);
                                    Map<String, String> queueParamMap = (Map<String, String>) val.get(queueId);
                                    String queueType = queueParamMap.get("queueType");
                                    vmParam.setQueueType(queueType);
                                    vmDefinition.setVmParam(vmParam);
                                    getConfigRegistry().getProcessDefinitions().add(vmDefinition);
                                }
                            }
                            break;
                            case TF:
                                break;
                            case BP:
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

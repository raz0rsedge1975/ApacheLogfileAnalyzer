package cons;

// GSON library:
// https://search.maven.org/artifact/com.google.code.gson/gson/2.8.6/jar
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
// Apache collections library:
// https://commons.apache.org/proper/commons-collections/download_collections.cgi
import cons.IPLocator;
import org.apache.commons.collections4.ListUtils;

import java.util.List;
import java.util.Map;
// Json to Map with Gson:
// https://www.baeldung.com/gson-json-to-map

public class LocateIPAdresses {
    IPLocator ipl = new IPLocator();
    Gson gson = new Gson();

    public String checkAdresses(String ip){
        StringBuilder sb = new StringBuilder();
        Map<String, String> map = gson.fromJson(ipl.locate(ip),new TypeToken<Map<String, String>>() {}.getType());

        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(" " + entry.getKey()).append(": ").append(entry.getValue()).append(", ");
        }
        return sb.toString();
    }

    public String checkAdresses(List<String> ips){
        StringBuilder sb = new StringBuilder();
        List<List<String>> output = ListUtils.partition(ips, 32);
        for (List<String> iplist : output) {
            sb.append(preparePrint(ipl.locate(iplist)));
        }
        return sb.toString();
    }

    private String preparePrint(String maxAddr){
        StringBuilder sb = new StringBuilder();
        Map<String, Map<String, String>> map = gson.fromJson(maxAddr, new TypeToken<Map<String, Map<String, String>>>() {}.getType());

        for (Map.Entry<String, Map<String, String>> outer : map.entrySet()) {
            sb.append(outer.getKey());
            for (Map.Entry<String, String> inner : outer.getValue().entrySet()) {
                sb.append("\n\t").append(inner.getKey()).append(" : ").append(inner.getValue());
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}

package analyzer;

import cons.LocateIPAdresses;
import cons.ValueComparator;

import java.util.*;

public class IPAnalyzer implements Analyzer{
    @Override
    public String analyze(String logContent) {
        LocateIPAdresses ipLocate = new LocateIPAdresses();
        StringBuilder sb = new StringBuilder("IP-Adress-Info:\n----------------------\n");
        int patternGroup = 1;
        long lines = 0;
        Map<String, Integer> outMap = new TreeMap<>();

        //Analyze Ip-Adresses
        lines = UserAgentAnalyzer.getLines(logContent, patternGroup, lines, outMap);
        ValueComparator comparator = new ValueComparator(outMap);
        SortedMap<String, Integer> sMap = new TreeMap<>(comparator);
        sMap.putAll(outMap);

        List<String> ipList = new ArrayList<>();
        ipList.addAll(sMap.keySet());
        //System.out.println(ipList); //debug

        for (Map.Entry<String, Integer> entry : sMap.entrySet()) {
                    sb.append("Occurrence: (").append(String.format("%.2f", ((double) entry.getValue() / lines * 100.)))
                    .append("%)")
                    .append(" -> ")
                    .append(ipLocate.checkAdresses(entry.getKey()))
                    .append(System.lineSeparator());
        }

        //sb.append(ipLocate.checkAdresses(ipList));
        sb.append("---------------EOF-----------------\n");

        return sb.toString();
    }
}

package net.oldschoolminecraft.yacf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class KeywordManager
{
    private final HashMap<String, Integer> blockedKeywords = new HashMap<>();
    private final HashMap<Integer, ArrayList<String>> replacementKeywordGroups = new HashMap<>();
    private final Random rng = new Random();
    private final File keywordFile;

    public KeywordManager(File keywordFile)
    {
        this.keywordFile = keywordFile;
    }

    public void reload()
    {
        blockedKeywords.clear();
        replacementKeywordGroups.clear();

        try(BufferedReader br = new BufferedReader(new FileReader(keywordFile)))
        {
            for(String line; (line = br.readLine()) != null;)
            {
                if (line.startsWith("#")) continue;
                String[] parts = line.split(":");
                int typeID = Integer.parseInt(parts[0]);
                String keyword = parts[1];
                int groupID = Integer.parseInt(parts[2]);

                if (typeID == 0) blockedKeywords.put(keyword, groupID);
                if (typeID == 1) replacementKeywordGroups.put(groupID, (ArrayList<String>) Collections.singletonList(keyword));
            }
        } catch (Exception e) {
            System.out.println("[YACF] Failed to read keyword file: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    public String process(String input)
    {
        String pre = input;
        String[] parts = input.split(" ");
        for (String part : parts)
        {
            if (!blockedKeywords.containsKey(part)) continue;
            int groupID = blockedKeywords.get(part);
            ArrayList<String> replacements = replacementKeywordGroups.get(groupID);
            pre = pre.replace(part, replacements.get(rng.nextInt(replacements.size())));
        }
        return pre;
    }
}

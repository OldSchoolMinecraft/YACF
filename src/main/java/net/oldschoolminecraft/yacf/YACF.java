package net.oldschoolminecraft.yacf;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Objects;

public class YACF extends JavaPlugin
{
    private KeywordManager keywordManager;

    public void onEnable()
    {
        File keywordFile = new File(getDataFolder(), "wordlist.txt");
        if (!keywordFile.exists()) extractResource("/wordlist.txt", keywordFile);
        keywordManager = new KeywordManager(keywordFile);
        keywordManager.reload();
        System.out.println("YACF enabled");
    }

    private void extractResource(String resource, File dst)
    {
        try (InputStream is = getClass().getResourceAsStream(resource))
        {
            try (FileOutputStream fos = new FileOutputStream(dst))
            {
                int b = Objects.requireNonNull(is).read();
                if (b == -1)
                {
                    fos.close();
                    is.close();
                    return;
                }
                fos.write(b);
            }
        } catch (Exception ex) {
            System.out.println("[YACF] Failed to extract resource: " + resource);
            ex.printStackTrace(System.err);
        }
    }

    public KeywordManager getKeywordManager()
    {
        return keywordManager;
    }

    public void onDisable()
    {
        System.out.println("YACF disabled");
    }
}

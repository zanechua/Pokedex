package com.radhi.Pokedex.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.other.Other;

public class ActivityAboutChangelog extends Activity {
    private final static String changelog = "" +
            "<h3 style=\"text-align: justify;\">Pok&eacute;dex ver 3</h3>\n" +
            "<ul style=\"text-align: justify;\">\n" +
            "<li>Major UI overhaul</li>\n" +
            "<li>Add list of location</li>\n" +
            "<li>Add Pok&eacute;mon evolution</li>\n" +
            "<li>Change Sugimori Art with bigger size and better quality</li>\n" +
            "<li>Add help page</li>\n" +
            "<li>Add donate page</li>\n" +
            "</ul>\n" +
            "<p style=\"text-align: justify;\">This new version has bigger APK size than the previous version, because the database in this version is not compressed. This make APK size bigger,&nbsp; but faster (because there is no need to extract data).</p>\n" +
            "<h3 style=\"text-align: justify;\">Pok&eacute;dex ver 1.5</h3>\n" +
            "<ul style=\"text-align: justify;\">\n" +
            "<li>Fix crash when opening Farfetch'd</li>\n" +
            "<li>Fix version name in Pok&eacute;mon move filter</li>\n" +
            "<li>Add advanced search for Pok&eacute;dex</li>\n" +
            "<li>Add info about available move for Pok&eacute;mon's alternative form</li>\n" +
            "</ul>\n" +
            "</div>\n" +
            "<h3 style=\"text-align: justify;\">Pok&eacute;dex ver 1.25</h3>\n" +
            "<ul style=\"text-align: justify;\">\n" +
            "<li>Add menu to download image</li>\n" +
            "<li>Add info about Pok&eacute;mon with multiple form, e.g. Unown, Vivilon, etc.</li>\n" +
            "<li>Add <em>Mega Evolution</em> data</li>\n" +
            "</ul>\n" +
            "<p style=\"text-align: justify;\">When downloading image, app will extract the data after download is finished. This may take several minutes, depending on your device.</p>\n" +
            "<h3 style=\"text-align: justify;\">Pok&eacute;dex ver 1</h3>\n" +
            "<ul style=\"text-align: justify;\">\n" +
            "<li>Improved performance and stability</li>\n" +
            "<li>Fix image not loaded in KitKat</li>\n" +
            "<li>Add some details in Pok&eacute;mon Move</li>\n" +
            "</ul>\n" +
            "<p style=\"text-align: justify;\">If you have previous version installed, it will be slow on first load because app will replace old database with the new one. If it takes too long, please clear data in System Setting &rarr; App &rarr; Pokedex &rarr; tap Clear data</p>\n" +
            "<h3 style=\"text-align: justify;\">Pok&eacute;dex ver 0.95</h3>\n" +
            "<ul>\n" +
            "<li style=\"text-align: justify;\">Initial release</li>\n" +
            "</ul>";

    private final static String about = "" +
            "<h3 style=\"text-align: justify;\">D&eacute;xDroid</h3>\n" +
            "<p style=\"text-align: justify;\">D&eacute;xDroid is Pok&eacute;dex (Pok&eacute;mon encyclopedia) for Android. It contains data of all Pok&eacute;mon species from every series of Pok&eacute;mon game (Gen I to Gen VI). Every Pok&eacute;mon's data is consisted by :</p>\n" +
            "<ul style=\"text-align: justify;\">\n" +
            "<li>Image (Sugimori Art and sprites)</li>\n" +
            "<li>Name (English, Japanese, and Romaji name)</li>\n" +
            "<li>Dex number for all region</li>\n" +
            "<li>Description (from all Pok&eacute;mon version)</li>\n" +
            "<li>Height</li>\n" +
            "<li>Weight</li>\n" +
            "<li>Ability</li>\n" +
            "<li>Type efficacy</li>\n" +
            "<li>Base stat</li>\n" +
            "<li>Move (separated by version group)</li>\n" +
            "<li>Location (separated by Pok&eacute;mon version)</li>\n" +
            "<li>Evolution (including Mega Evolution)</li>\n" +
            "<li>Etc.</li>\n" +
            "</ul>\n" +
            "<h3 style=\"text-align: justify;\">License</h3>\n" +
            "<p style=\"text-align: justify;\">This app is released under <a href=\"http://choosealicense.com/licenses/gpl-v3/\">GPLv3 License</a> and its source code is available in my <a href=\"https://github.com/Acrophobic/Pokedex\">Github</a>. Everyone is allowed to modify this app and release it in their own name, but they have to open the source code. And, if you would, please give proper credit to me.</p>\n" +
            "<h3 style=\"text-align: justify;\">Sources</h3>\n" +
            "<p style=\"text-align: justify;\">Database is taken from Veekun's git (<a href=\"http://git.veekun.com/pokedex.git/tree/HEAD:/pokedex/data/csv\">here</a> or <a href=\"https://github.com/veekun/pokedex/tree/master/pokedex/data/csv\">here</a>). I just convert it from CSV to SQLITE.</p>\n" +
            "<p style=\"text-align: justify;\">Images and sprites are taken from :</p>\n" +
            "<ul style=\"text-align: justify;\">\n" +
            "<li>Veekun's <a href=\"http://git.veekun.com/pokedex-media.git\">git</a> and his <a href=\"http://veekun.com/dex/downloads\">site</a></li>\n" +
            "<li><a href=\"http://bulbapedia.bulbagarden.net\">Bulbapedia</a>, the community driven Pok&eacute;mon encyclopedia</li>\n" +
            "<li><a href=\"http://www.legendarypokemon.net/\">LegendaryPokemon</a>, where I took some of Sugimori Art</li>\n" +
            "<li><a href=\"http://www.serebii.net\">Serebii</a>, where I took some sprites for Pok&eacute;mon X/Y and Mega Evolution</li>\n" +
            "</ul>\n" +
            "<h3 style=\"text-align: justify;\">Acknowledgements</h3>\n" +
            "<p style=\"text-align: justify;\">In this app, I use following library / tools :</p>\n" +
            "<ul>\n" +
            "<li style=\"text-align: justify;\"><a href=\"https://github.com/jgilfelt/android-sqlite-asset-helper\">Android SQLiteAssetHelper</a> library by <a href=\"https://github.com/jgilfelt\">Jeff Gilfelt</a></li>\n" +
            "<li style=\"text-align: justify;\"><a href=\"https://github.com/jeromevdl/android-holo-colors-idea-plugin\">Android Holo Colors Plugin</a>&nbsp;by <a href=\"https://github.com/jeromevdl\"><span class=\"vcard-fullname\">J&eacute;r&ocirc;me Van Der Linden</span></a></li>\n" +
            "</ul>";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changelog_about);
        getWindow().setBackgroundDrawable(null);

        TextView txtOK = (TextView) findViewById(R.id.txtOkChngAbout);
        WebView viewChangelog = (WebView) findViewById(R.id.viewAboutChangelog);

        int AboutOrChange = getIntent().getIntExtra(Other.AboutOrChange,0);

        if (AboutOrChange == 0) {
            setTitle("Changelog");
            viewChangelog.loadData(changelog, "text/html", "UTF-8");
        } else {
            setTitle("About");
            viewChangelog.loadData(about, "text/html", "UTF-8");
        }

        txtOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

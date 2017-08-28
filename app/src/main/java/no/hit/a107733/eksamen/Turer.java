package no.hit.a107733.eksamen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Håkon on 29.05.2017.
 */

public class Turer {
    // Objektvariabler som samsvarer med kolonner i databasetabellen
    String navn;
    String type;
    String beskrivelse;
    String lengde;
    int breddegrad;
    int lengdegrad;
    int moh;
    String regav;
    String url;



    // Tabellnavn og kolonnenavn for tabellen Vare i databasen
    static final String TABELL_NAVN = "Turer";
    static final String KOL_NAVN_NAVN = "Navn";
    static final String KOL_NAVN_TYPE = "Type";
    static final String KOL_NAVN_BESKRIVELSE = "Beskrivelse";
    static final String KOL_NAVN_LENGDE = "Lengde";
    static final String KOL_NAVN_BREDDEGRAD = "Breddegrad";
    static final String KOL_NAVN_LENGDEGRAD = "Lengdegrad";
    static final String KOL_NAVN_MOH = "Meter over havet";
    static final String KOL_NAVN_REGAV = "Registrert av";
    static final String KOL_NAVN_URL = "Url";

    // "Vanlig" konstruktør
    public Turer(String navn, String type, String beskrivelse, String lengde, int breddegrad, int lengdegrad, int moh, String regav, String url) {
        this.navn = navn;
        this.type = type;
        this.beskrivelse = beskrivelse;
        this.lengde = lengde;
        this.lengdegrad = lengdegrad;
        this.breddegrad = breddegrad;
        this.moh = moh;
        this.regav = regav;
        this.url = url;
    }

    // Tom konstruktør
    public Turer() {}


    public Turer(JSONObject jsonTurer) {
        this.navn = jsonTurer.optString(KOL_NAVN_NAVN);
        this.type = jsonTurer.optString(KOL_NAVN_TYPE);
        this.beskrivelse = jsonTurer.optString(KOL_NAVN_BESKRIVELSE);
        this.lengde = jsonTurer.optString(KOL_NAVN_LENGDE);
        this.breddegrad = jsonTurer.optInt(KOL_NAVN_BREDDEGRAD);
        this.lengdegrad = jsonTurer.optInt(KOL_NAVN_LENGDEGRAD);
        this.moh = jsonTurer.optInt(KOL_NAVN_MOH);
        this.regav = jsonTurer.optString(KOL_NAVN_REGAV);
        this.url = jsonTurer.optString(KOL_NAVN_URL);

    }
    // Metode som lager en ArrayList med Vare-objekter basert på en streng med JSONdata

    public static ArrayList<Turer> lagTurListe(String jsonTurer) throws JSONException, NullPointerException {
        ArrayList<Turer> turListe = new ArrayList<Turer>();
        JSONObject jsonData = new JSONObject(jsonTurer);
        JSONArray jsonTurTabell = jsonData.optJSONArray(TABELL_NAVN);
        for(int i = 0; i < jsonTurTabell.length(); i++) {
            JSONObject jsonFylke = (JSONObject) jsonTurTabell.get(i);
            Turer denneTuren = new Turer(jsonFylke);
            turListe.add(denneTuren);
        }
        return turListe;
    }
    @Override
    public String toString() { return navn + "                        " + "Lengde: " + lengde;  }
}


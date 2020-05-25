package com.example.try2.Files;

import android.content.Context;
import android.widget.Toast;
import com.example.try2.Activities.SecondActivity;
import com.example.try2.R;
import org.xmlpull.v1.XmlPullParser;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class voidFiles {

    private Context context;
    private static int cur;
    private static String curStr;
    private static String str;
    private static double latitude;
    private static double longitude;
    private static double latitudePlus;
    private static double longitudePlus;
    private static double latitudeMinus;
    private static double longitudeMinus;
    ArrayList<Loc> loc = new ArrayList<>();
    private ArrayList<Files> files = new ArrayList<>();

    public voidFiles(Context current) {

        this.context = current;
    }

    public void readFileXML(int num) {

        switch (num) {
            case 0:
                try {
                    XmlPullParser parser = context.getResources().getXml(R.xml.data);

                    while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {

                        if (parser.getEventType() == XmlPullParser.START_TAG) {

                            if (parser.getName().equals("OrgFullName")) {

                                parser.next();
                                if (parser.getEventType() == XmlPullParser.TEXT) {

                                    Pattern pattern = Pattern.compile("[0-9]{6}");
                                    Matcher matcher = pattern.matcher("" + parser.getText());
                                    Pattern pattern1 = Pattern.compile("Закон Российской Федерации");
                                    Matcher matcher1 = pattern1.matcher("" + parser.getText());
                                    if (!matcher.find() && !matcher1.find()) {

                                        files.add(new Files(null,parser.getText().substring(0, 1).toUpperCase() + parser.getText().substring(1)));
                                    }
                                }
                            }
                        }
                        parser.next();
                    }
                } catch (Throwable t) {
                    Toast.makeText(context,
                            "Ошибка при загрузке XML-документа: " + t.toString(),
                            Toast.LENGTH_LONG).show();
                }
                break;
            case 1:
                try {
                    XmlPullParser parser = context.getResources().getXml(R.xml.data);

                    int count = 0;
                    while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                        if (parser.getEventType() == XmlPullParser.START_TAG && parser.getDepth() == 2) {
                            if (count == cur) {
                                parser.next();
                                while (parser.getDepth() > 2) {
                                    if(parser.getEventType() == XmlPullParser.START_TAG)
                                    {
                                        switch (parser.getName())
                                        {
                                            case "NameOfAccreditingAuthority":
                                                str = "Наименование аккредитующего органа: ";
                                                break;
                                            case "OrgType":
                                                str = "Тип организации: ";
                                                break;
                                            case "OrgFullName":
                                                str = "Полное название организации: ";
                                                break;
                                            case "OrgShortName":
                                                str = "Краткое название организации: ";
                                                break;
                                            case "OrgFirmName":
                                                str = "Фирменное наименование организации: ";
                                                break;
                                            case "PhoneNumber":
                                                str = "Телефонный номер: ";
                                                break;
                                            case "EMail":
                                                str = "EMail: ";
                                                break;
                                            case "FormOfIncorporation":
                                                str = "Форма регистрации: ";
                                                break;
                                            case "Latitude":
                                                str = "Широта: ";
                                                break;
                                            case "Longitude":
                                                str = "Долгота: ";
                                                break;
                                            case "Address":
                                                str = "Адрес: ";
                                                break;
                                            case "KLADR":
                                                str = "КЛАДР(Классификация адресов Российской Федерации): ";
                                                break;
                                            case "OGRN":
                                                str = "ОГРН(Основной государственный регистрационный номер): ";
                                                break;
                                            case "INN":
                                                str = "ИНН(Идентификационный номер налогоплательщика): ";
                                                break;
                                            case "WorkPlacesAddresses":
                                                str = "Адреса рабочих мест: ";
                                                break;
                                            case "Services":
                                                str = "Услуги: ";
                                                break;
                                            case "SvidDate":
                                                str = "Дата СВИД: ";
                                                break;
                                            case "SvidNumber":
                                                str = "Номер СВИД: ";
                                                break;
                                            case "SvidExpireDate":
                                                str = "Дата истечения СВИД: ";
                                                break;
                                            case "OrderDate":
                                                str = "Дата заказа: ";
                                                break;
                                            case "OrderNumber":
                                                str = "Номер заказа: ";
                                                break;
                                            case "RegisterEntryDate":
                                                str = "Дата записи в регистр: ";
                                                break;
                                            case "SvidTerminationDate":
                                                str = "Дата прекращения действия СВИД: ";
                                                break;
                                            case "SvidTerminationCause":
                                                str = "Причина прекращения СВИД: ";
                                                break;
                                            case "Inspections":
                                                str = "Экспертиза: ";
                                                break;
                                            case "AdministrativeSuspensions":
                                                str = "Административное приостановление деятельности: ";
                                                break;
                                            case "SvidSuspensions":
                                                str = "Приостановка СВИД: ";
                                                break;
                                            case "SvidRevocation":
                                                str = "Аннулирование СВИД: ";
                                                break;
                                            default:
                                                str = "";
                                                break;
                                        }
                                    }
                                    if (parser.getEventType() == XmlPullParser.TEXT) {
                                        if (parser.getText() != null) {
                                            if(str.equals("Широта: "))
                                            {
                                                latitude = Double.parseDouble(parser.getText());
                                            }
                                            if(str.equals("Долгота: "))
                                            {
                                                longitude = Double.parseDouble(parser.getText());
                                            }

                                            files.add(new Files(str, parser.getText().substring(0, 1).toUpperCase() + parser.getText().substring(1)));
                                        }
                                    }
                                    parser.next();
                                }
                                break;
                            } else {
                                count++;
                            }
                        }
                        parser.next();
                    }
                } catch (Throwable t) {
                    Toast.makeText(context,
                            "Ошибка при загрузке XML-документа: " + t.toString(),
                            Toast.LENGTH_LONG).show();
                }
                SecondActivity.putMap(16, latitude, longitude);
                break;
            case 2:
                try {
                    XmlPullParser parser = context.getResources().getXml(R.xml.data);

                    while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                        if (parser.getEventType() == XmlPullParser.START_TAG) {
                            if (parser.getName().equals("OrgFullName")) {
                                parser.next();
                                if (parser.getEventType() == XmlPullParser.TEXT) {
                                    Pattern pattern = Pattern.compile("[0-9]{6}");
                                    Matcher matcher = pattern.matcher("" + parser.getText());
                                    Pattern pattern1 = Pattern.compile(curStr);
                                    Matcher matcher1 = pattern1.matcher("" + parser.getText());
                                    Pattern pattern2 = Pattern.compile("Закон Российской Федерации");
                                    Matcher matcher2 = pattern2.matcher("" + parser.getText());
                                    if (!matcher.find() && matcher1.find() && !matcher2.find()) {
                                        files.add(new Files(null,parser.getText().substring(0, 1).toUpperCase() + parser.getText().substring(1)));
                                    }
                                }
                            }
                        }
                        parser.next();
                    }
                } catch (Throwable t) {
                    Toast.makeText(context,
                            "Ошибка при загрузке XML-документа: " + t.toString(),
                            Toast.LENGTH_LONG).show();
                }
                break;
            case 3:
                try {
                    XmlPullParser parser = context.getResources().getXml(R.xml.data);

                    while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                        if (parser.getEventType() == XmlPullParser.START_TAG) {
                            if(parser.getName().equals("Latitude"))
                            {
                                parser.next();
                                if (parser.getEventType() == XmlPullParser.TEXT) {
                                    latitude = Double.parseDouble(parser.getText());

                                    parser.next();
                                    parser.next();
                                }
                                if (parser.getName().equals("Longitude")) {
                                    parser.next();
                                    if (parser.getEventType() == XmlPullParser.TEXT) {
                                        longitude = Double.parseDouble(parser.getText());
                                    }
                                }
                                loc.add(new Loc(latitude, longitude));
                            }
                        }
                        parser.next();
                    }
                } catch (Throwable t) {
                    Toast.makeText(context,
                            "Ошибка при загрузке XML-документа: " + t.toString(),
                            Toast.LENGTH_LONG).show();
                }
                break;
            case 4:
                try {
                    XmlPullParser parser = context.getResources().getXml(R.xml.data);

                    int count = 0;
                    while (parser.getEventType() != XmlPullParser.END_DOCUMENT)
                    {
                        if (parser.getEventType() == XmlPullParser.START_TAG && parser.getDepth() == 2)
                        {
                            parser.next();
                            while (parser.getDepth() > 2)
                            {
                                if (parser.getEventType() == XmlPullParser.START_TAG)
                                {
                                    if (parser.getName().equals("Latitude"))
                                    {
                                        parser.next();
                                        if (parser.getEventType() == XmlPullParser.TEXT)
                                        {
                                            if (Double.parseDouble(parser.getText()) > latitudeMinus && Double.parseDouble(parser.getText()) < latitudePlus)
                                            {

                                                parser.next();
                                                parser.next();

                                                if (parser.getName().equals("Longitude"))
                                                {
                                                    parser.next();
                                                    if (parser.getEventType() == XmlPullParser.TEXT)
                                                    {
                                                        //Log.i("LogCat-Pre-Parser2", "" + parser.getText());
                                                        if (Double.parseDouble(parser.getText()) > longitudeMinus && Double.parseDouble(parser.getText()) < longitudePlus)
                                                        {
                                                            //Log.i("LogCat-Parser2", "" + parser.getText());

                                                            XmlPullParser parser2 = context.getResources().getXml(R.xml.data);

                                                            int count2 = 0;
                                                            while (parser2.getEventType() != XmlPullParser.END_DOCUMENT) {
                                                                if (parser2.getEventType() == XmlPullParser.START_TAG && parser2.getDepth() == 2) {
                                                                    if (count2 == count) {
                                                                        parser2.next();
                                                                        while (parser2.getDepth() > 2) {
                                                                            if (parser2.getEventType() == XmlPullParser.START_TAG) {
                                                                                switch (parser2.getName()) {
                                                                                    case "NameOfAccreditingAuthority":
                                                                                        str = "Наименование аккредитующего органа: ";
                                                                                        break;
                                                                                    case "OrgType":
                                                                                        str = "Тип организации: ";
                                                                                        break;
                                                                                    case "OrgFullName":
                                                                                        str = "Полное название организации: ";
                                                                                        break;
                                                                                    case "OrgShortName":
                                                                                        str = "Краткое название организации: ";
                                                                                        break;
                                                                                    case "OrgFirmName":
                                                                                        str = "Фирменное наименование организации: ";
                                                                                        break;
                                                                                    case "PhoneNumber":
                                                                                        str = "Телефонный номер: ";
                                                                                        break;
                                                                                    case "EMail":
                                                                                        str = "EMail: ";
                                                                                        break;
                                                                                    case "FormOfIncorporation":
                                                                                        str = "Форма регистрации: ";
                                                                                        break;
                                                                                    case "Latitude":
                                                                                        str = "Широта: ";
                                                                                        break;
                                                                                    case "Longitude":
                                                                                        str = "Долгота: ";
                                                                                        break;
                                                                                    case "Address":
                                                                                        str = "Адрес: ";
                                                                                        break;
                                                                                    case "KLADR":
                                                                                        str = "КЛАДР(Классификация адресов Российской Федерации): ";
                                                                                        break;
                                                                                    case "OGRN":
                                                                                        str = "ОГРН(Основной государственный регистрационный номер): ";
                                                                                        break;
                                                                                    case "INN":
                                                                                        str = "ИНН(Идентификационный номер налогоплательщика): ";
                                                                                        break;
                                                                                    case "WorkPlacesAddresses":
                                                                                        str = "Адреса рабочих мест: ";
                                                                                        break;
                                                                                    case "Services":
                                                                                        str = "Услуги: ";
                                                                                        break;
                                                                                    case "SvidDate":
                                                                                        str = "Дата СВИД: ";
                                                                                        break;
                                                                                    case "SvidNumber":
                                                                                        str = "Номер СВИД: ";
                                                                                        break;
                                                                                    case "SvidExpireDate":
                                                                                        str = "Дата истечения СВИД: ";
                                                                                        break;
                                                                                    case "OrderDate":
                                                                                        str = "Дата заказа: ";
                                                                                        break;
                                                                                    case "OrderNumber":
                                                                                        str = "Номер заказа: ";
                                                                                        break;
                                                                                    case "RegisterEntryDate":
                                                                                        str = "Дата записи в регистр: ";
                                                                                        break;
                                                                                    case "SvidTerminationDate":
                                                                                        str = "Дата прекращения действия СВИД: ";
                                                                                        break;
                                                                                    case "SvidTerminationCause":
                                                                                        str = "Причина прекращения СВИД: ";
                                                                                        break;
                                                                                    case "Inspections":
                                                                                        str = "Экспертиза: ";
                                                                                        break;
                                                                                    case "AdministrativeSuspensions":
                                                                                        str = "Административное приостановление деятельности: ";
                                                                                        break;
                                                                                    case "SvidSuspensions":
                                                                                        str = "Приостановка СВИД: ";
                                                                                        break;
                                                                                    case "SvidRevocation":
                                                                                        str = "Аннулирование СВИД: ";
                                                                                        break;
                                                                                    default:
                                                                                        str = "";
                                                                                        break;
                                                                                }
                                                                            }
                                                                            if (parser2.getEventType() == XmlPullParser.TEXT) {
                                                                                if (parser2.getText() != null) {
                                                                                    files.add(new Files(str, parser2.getText().substring(0, 1).toUpperCase() + parser2.getText().substring(1)));
                                                                                }
                                                                            }
                                                                            parser2.next();
                                                                        }
                                                                    }
                                                                    count2++;
                                                                }
                                                                parser2.next();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                parser.next();
                            }
                            count++;
                        }
                        parser.next();
                    }
                } catch (Throwable t) {
                    Toast.makeText(context,
                            "Ошибка при загрузке XML-документа: " + t.toString(),
                            Toast.LENGTH_LONG).show();
                }
                break;
            case 5:
                try {
                    XmlPullParser parser = context.getResources().getXml(R.xml.data);
                    int Ocount = -1;
                    int count = 0;
                    while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                        if (parser.getEventType() == XmlPullParser.START_TAG) {
                            if (parser.getName().equals("OrgFullName")) {
                                Ocount++;
                                parser.next();
                                if (parser.getEventType() == XmlPullParser.TEXT) {
                                    Pattern pattern = Pattern.compile("[0-9]{6}");
                                    Matcher matcher = pattern.matcher("" + parser.getText());
                                    Pattern pattern1 = Pattern.compile(curStr);
                                    Matcher matcher1 = pattern1.matcher("" + parser.getText());
                                    Pattern pattern2 = Pattern.compile("Закон Российской Федерации");
                                    Matcher matcher2 = pattern2.matcher("" + parser.getText());
                                    if (!matcher.find() && matcher1.find() && !matcher2.find()) {
                                        //Log.i("LogCat", count + "<-count " + cur  + "<-cur " + Ocount + "<-Ocount " + curStr + "<-curStr >>> " + parser.getText());
                                        if(cur == count)
                                        {
                                            count = Ocount;
                                            //Log.i("LogCat", count + "<-count " + cur  + "<-cur " + Ocount + "<-Ocount " + curStr + "<-curStr >>> " + parser.getText());
                                            XmlPullParser parser2 = context.getResources().getXml(R.xml.data);

                                            int count2 = 0;
                                            while (parser2.getEventType() != XmlPullParser.END_DOCUMENT) {
                                                if (parser2.getEventType() == XmlPullParser.START_TAG && parser2.getDepth() == 2) {
                                                    if (count2 == count) {
                                                        parser2.next();
                                                        while (parser2.getDepth() > 2) {
                                                            if (parser2.getEventType() == XmlPullParser.START_TAG) {
                                                                switch (parser2.getName()) {
                                                                    case "NameOfAccreditingAuthority":
                                                                        str = "Наименование аккредитующего органа: ";
                                                                        break;
                                                                    case "OrgType":
                                                                        str = "Тип организации: ";
                                                                        break;
                                                                    case "OrgFullName":
                                                                        str = "Полное название организации: ";
                                                                        break;
                                                                    case "OrgShortName":
                                                                        str = "Краткое название организации: ";
                                                                        break;
                                                                    case "OrgFirmName":
                                                                        str = "Фирменное наименование организации: ";
                                                                        break;
                                                                    case "PhoneNumber":
                                                                        str = "Телефонный номер: ";
                                                                        break;
                                                                    case "EMail":
                                                                        str = "EMail: ";
                                                                        break;
                                                                    case "FormOfIncorporation":
                                                                        str = "Форма регистрации: ";
                                                                        break;
                                                                    case "Latitude":
                                                                        str = "Широта: ";
                                                                        break;
                                                                    case "Longitude":
                                                                        str = "Долгота: ";
                                                                        break;
                                                                    case "Address":
                                                                        str = "Адрес: ";
                                                                        break;
                                                                    case "KLADR":
                                                                        str = "КЛАДР(Классификация адресов Российской Федерации): ";
                                                                        break;
                                                                    case "OGRN":
                                                                        str = "ОГРН(Основной государственный регистрационный номер): ";
                                                                        break;
                                                                    case "INN":
                                                                        str = "ИНН(Идентификационный номер налогоплательщика): ";
                                                                        break;
                                                                    case "WorkPlacesAddresses":
                                                                        str = "Адреса рабочих мест: ";
                                                                        break;
                                                                    case "Services":
                                                                        str = "Услуги: ";
                                                                        break;
                                                                    case "SvidDate":
                                                                        str = "Дата СВИД: ";
                                                                        break;
                                                                    case "SvidNumber":
                                                                        str = "Номер СВИД: ";
                                                                        break;
                                                                    case "SvidExpireDate":
                                                                        str = "Дата истечения СВИД: ";
                                                                        break;
                                                                    case "OrderDate":
                                                                        str = "Дата заказа: ";
                                                                        break;
                                                                    case "OrderNumber":
                                                                        str = "Номер заказа: ";
                                                                        break;
                                                                    case "RegisterEntryDate":
                                                                        str = "Дата записи в регистр: ";
                                                                        break;
                                                                    case "SvidTerminationDate":
                                                                        str = "Дата прекращения действия СВИД: ";
                                                                        break;
                                                                    case "SvidTerminationCause":
                                                                        str = "Причина прекращения СВИД: ";
                                                                        break;
                                                                    case "Inspections":
                                                                        str = "Экспертиза: ";
                                                                        break;
                                                                    case "AdministrativeSuspensions":
                                                                        str = "Административное приостановление деятельности: ";
                                                                        break;
                                                                    case "SvidSuspensions":
                                                                        str = "Приостановка СВИД: ";
                                                                        break;
                                                                    case "SvidRevocation":
                                                                        str = "Аннулирование СВИД: ";
                                                                        break;
                                                                    default:
                                                                        str = "";
                                                                        break;
                                                                }
                                                            }
                                                            if (parser2.getEventType() == XmlPullParser.TEXT) {
                                                                if (parser2.getText() != null) {
                                                                    if(str.equals("Широта: "))
                                                                    {
                                                                        latitude = Double.parseDouble(parser2.getText());
                                                                    }
                                                                    if(str.equals("Долгота: "))
                                                                    {
                                                                        longitude = Double.parseDouble(parser2.getText());
                                                                    }

                                                                    files.add(new Files(str, parser2.getText().substring(0, 1).toUpperCase() + parser2.getText().substring(1)));
                                                                }
                                                            }
                                                            parser2.next();
                                                        }
                                                    }
                                                    count2++;
                                                }
                                                parser2.next();
                                            }
                                        }
                                        count++;
                                    }
                                }
                            }
                        }
                        parser.next();
                    }
                } catch (Throwable t) {
                    Toast.makeText(context,
                            "Ошибка при загрузке XML-документа: " + t.toString(),
                            Toast.LENGTH_LONG).show();
                }
                SecondActivity.putMap(16, latitude, longitude);
                break;
            default:
                break;
        }
    }

    public static void putPMInt(double laP, double laM, double loP, double loM) {

        latitudePlus = laP;
        latitudeMinus = laM;
        longitudePlus = loP;
        longitudeMinus = loM;
    }

    public static void putCur(int current) {

        cur = current;
    }

    public static void putCurStr(String current) {

        curStr = current;
    }

    public ArrayList<Files> getFileXML() {

        return files;
    }

    public ArrayList<Loc> getLoc() {

        return loc;
    }

}
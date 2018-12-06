package com.hzgc.manage.service.impl;


import com.google.gson.Gson;
import com.hzgc.jniface.FaceAttribute;
import com.hzgc.jniface.FaceFunction;
import com.hzgc.jniface.FaceUtil;
import com.hzgc.jniface.PictureFormat;
import com.hzgc.load.Base64Utils;
import com.hzgc.load.JsonUtils;
import com.hzgc.manage.dao.*;
import com.hzgc.manage.entity.*;
import com.hzgc.manage.service.PersonService;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.*;

/**
 * 用户服务层实现
 * created by liang on 18-11-16
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProvincesRepository provincesRepository;

    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private AreasRepository areasRepository;

    @Autowired
    private UserRepository userRepository;

    private static String StrJson = null;
    private static String strImageName = null;
    private static List<Person> list = new ArrayList<>();


    @Override
    @Transactional
    public void insert(String dirPath) throws IOException {
        FaceFunction.init();
        getFileList(dirPath);
    }


    private void getFileList(String strPath) throws IOException {
        extractFeature(strPath);
        inputES();
    }

    private static String xml2jsonString(File file) throws IOException {
        InputStream in = new FileInputStream(file);
        String xml = IOUtils.toString(in, "utf-8");
        JSONObject xmlJSONObj = XML.toJSONObject(xml);
        return xmlJSONObj.toString();
    }

    private void extractFeature(String strPath) throws IOException {
        File dir = new File(strPath);

        // 该文件目录下文件全部放入数组
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                // 判断是文件还是文件夹
                if (file.isDirectory()) {
                    // 获取文件绝对路径
                    extractFeature(file.getAbsolutePath());
                } else if (fileName.endsWith("xml")) { // 判断文件名是否以.xml结尾
                    String strFileName = file.getAbsolutePath();
                    System.out.println("xml---" + strFileName);
                    String s = xml2jsonString(file);
                    Gson gson = new Gson();
                    People people = gson.fromJson(s, People.class);
                    people.getPerson().setTpbase(StrJson);
                    people.getPerson().setTp(strImageName);
//                    System.out.println("StrJson::"+StrJson);

                    try {
                        FaceAttribute faceAttribute = FaceFunction.faceFeatureExtract(Base64Utils.base64Str2BinArry(StrJson), PictureFormat.JPG);
                        people.getPerson().setBittzz(FaceUtil.bitFeautre2Base64Str(faceAttribute.getBitFeature()));
                        people.getPerson().setTzz(FaceUtil.floatFeature2Base64Str(faceAttribute.getFeature()));
                        people.getPerson().setId(UUID.randomUUID().toString().replace("-", ""));
                    } catch (NullPointerException e) {
                        FileWriter fw = new FileWriter("/srv/failed.txt", true);
                        System.out.println("failed filename:" + strFileName);
                        fw.write(strFileName + "\n");
                        fw.close();
                        continue;
                    }
                    StrJson = null;
                    list.add(people.getPerson());

                    if (list.size() >= 50000) {
                        inputES();
                        list.clear();
                    }

                    try {
                        String[] path=strFileName.split("/");
                        FileWriter fw = new FileWriter("/srv/" + path[path.length-2].substring(0,4) + ".txt", true);
                        fw.write(people.getPerson().getId() + "_" + people.getPerson().getBittzz() + "\n");
                        fw.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (fileName.endsWith("jpeg")) { // 判断文件名是否以.jpeg结尾
                    strImageName = file.getAbsolutePath();
                    System.out.println("jpeg---" + strImageName);
                    StrJson = Base64Utils.getImageStr(strImageName);
//                    FaceAttribute faceAttribute = FaceFunction.faceFeatureExtract(Base64Utils.base64Str2BinArry(StrJson), PictureFormat.JPG);
//                    String bittzz = FaceUtil.bitFeautre2Base64Str(faceAttribute.getBitFeature());
//                    String ttz = FaceUtil.floatFeature2Base64Str(faceAttribute.getFeature());
//
//                    try {
//                        FileWriter fw = new FileWriter("/srv/compare_index.txt", true);
//                        fw.write(strImageName+"___"+StrJson+"___"+bittzz+"___"+ttz+ "\n");
//                        fw.close();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                }
            }
        }
    }



    @Override
    public void importdata(String province) {


        this.saveUser();
        this.saveProvinces();
        this.saveCities();

        try {
            ArrayList<Areas> areasList = new ArrayList<>();
            InputStream inputStream =  getClass().getClassLoader().getResourceAsStream("Areas.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                String s[] = line.split(",");
                Areas areas = new Areas(s[0], s[1], s[2], s[3]);
                areasList.add(areas);
            }
            areasRepository.saveAll(areasList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void inputES() {
        for (ListIterator<Person> it = list.listIterator(); it.hasNext(); ) {
            Person person = it.next();

            System.out.println("personID:"+person.getId().toString());
            System.out.println("personSfz:"+person.getSfz().toString());
            System.out.println("personXm:"+person.getXm().toString());
            System.out.println("personXb:"+person.getXb().toString());
            System.out.println("personMz:"+person.getMz().toString());
            System.out.println("personSr:"+person.getSr().toString());
            System.out.println("personSsssqx:"+person.getSsssqx().toString());
            System.out.println("personMlxz:"+person.getMlxz().toString());
            System.out.println("personJd:"+person.getJd().toString());
            System.out.println("personMp:"+person.getMp().toString());
            System.out.println("personJg:"+person.getJg().toString());
            System.out.println("personTp:"+person.getTp().toString());
            System.out.println("personCym:"+person.getCym().toString());
            System.out.println("personCsd:"+person.getCsd().toString());
            System.out.println("personTzz:"+person.getTzz().toString());
            System.out.println("personBit:"+person.getBittzz().toString());

        }
//        list.clear();
        if (list.size()!=0) {
            personRepository.saveAll(list);
            list.clear();
        }

    }

    private void saveProvinces(){

        ArrayList<Provinces> provincesList = new ArrayList<>();
        provincesList.add(new Provinces("1","110000","北京市"));
        provincesList.add(new Provinces("2","120000","天津市"));
        provincesList.add(new Provinces("3","130000","河北省"));
        provincesList.add(new Provinces("4","140000","山西省"));
        provincesList.add(new Provinces("5","150000","内蒙古自治区"));
        provincesList.add(new Provinces("6","210000","辽宁省"));
        provincesList.add(new Provinces("7","220000","吉林省"));
        provincesList.add(new Provinces("8","230000","黑龙江省"));
        provincesList.add(new Provinces("9","310000","上海市"));
        provincesList.add(new Provinces("10","320000","江苏省"));
        provincesList.add(new Provinces("11","330000","浙江省"));
        provincesList.add(new Provinces("12","340000","安徽省"));
        provincesList.add(new Provinces("13","350000","福建省"));
        provincesList.add(new Provinces("14","360000","江西省"));
        provincesList.add(new Provinces("15","370000","山东省"));
        provincesList.add(new Provinces("16","410000","河南省"));
        provincesList.add(new Provinces("17","420000","湖北省"));
        provincesList.add(new Provinces("18","430000","湖南省"));
        provincesList.add(new Provinces("19","440000","广东省"));
        provincesList.add(new Provinces("20","450000","广西壮族自治区"));
        provincesList.add(new Provinces("21","460000","海南省"));
        provincesList.add(new Provinces("22","500000","重庆市"));
        provincesList.add(new Provinces("23","510000","四川省"));
        provincesList.add(new Provinces("24","520000","贵州省"));
        provincesList.add(new Provinces("25","530000","云南省"));
        provincesList.add(new Provinces("26","540000","西藏自治区"));
        provincesList.add(new Provinces("27","610000","陕西省"));
        provincesList.add(new Provinces("28","620000","甘肃省"));
        provincesList.add(new Provinces("29","630000","青海省"));
        provincesList.add(new Provinces("30","640000","宁夏回族自治区"));
        provincesList.add(new Provinces("31","650000","新疆维吾尔自治区"));
        provincesList.add(new Provinces("32","710000","台湾省"));
        provincesList.add(new Provinces("33","810000","香港特别行政区"));
        provincesList.add(new Provinces("34","820000","澳门特别行政区"));

        provincesRepository.saveAll(provincesList);

    }

    private void saveUser(){
        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User("6d01247799b948959d209f3a89872882","admin","d3b1294a61a07da9b49b6e22b2cbd7f9",1,"123456",new Date()));
        userRepository.saveAll(userList);
    }

    private void saveCities(){

        ArrayList<Cities> citiesList = new ArrayList<>();
        citiesList.add(new Cities("1","110100","市辖区","110000"));
        citiesList.add(new Cities("2","110200","县","110000"));
        citiesList.add(new Cities("3","120100","市辖区","120000"));
        citiesList.add(new Cities("4","120200","县","120000"));
        citiesList.add(new Cities("5","130100","石家庄市","130000"));
        citiesList.add(new Cities("6","130200","唐山市","130000"));
        citiesList.add(new Cities("7","130300","秦皇岛市","130000"));
        citiesList.add(new Cities("8","130400","邯郸市","130000"));
        citiesList.add(new Cities("9","130500","邢台市","130000"));
        citiesList.add(new Cities("10","130600","保定市","130000"));
        citiesList.add(new Cities("11","130700","张家口市","130000"));
        citiesList.add(new Cities("12","130800","承德市","130000"));
        citiesList.add(new Cities("13","130900","沧州市","130000"));
        citiesList.add(new Cities("14","131000","廊坊市","130000"));
        citiesList.add(new Cities("15","131100","衡水市","130000"));
        citiesList.add(new Cities("16","140100","太原市","140000"));
        citiesList.add(new Cities("17","140200","大同市","140000"));
        citiesList.add(new Cities("18","140300","阳泉市","140000"));
        citiesList.add(new Cities("19","140400","长治市","140000"));
        citiesList.add(new Cities("20","140500","晋城市","140000"));
        citiesList.add(new Cities("21","140600","朔州市","140000"));
        citiesList.add(new Cities("22","140700","晋中市","140000"));
        citiesList.add(new Cities("23","140800","运城市","140000"));
        citiesList.add(new Cities("24","140900","忻州市","140000"));
        citiesList.add(new Cities("25","141000","临汾市","140000"));
        citiesList.add(new Cities("26","141100","吕梁市","140000"));
        citiesList.add(new Cities("27","150100","呼和浩特市","150000"));
        citiesList.add(new Cities("28","150200","包头市","150000"));
        citiesList.add(new Cities("29","150300","乌海市","150000"));
        citiesList.add(new Cities("30","150400","赤峰市","150000"));
        citiesList.add(new Cities("31","150500","通辽市","150000"));
        citiesList.add(new Cities("32","150600","鄂尔多斯市","150000"));
        citiesList.add(new Cities("33","150700","呼伦贝尔市","150000"));
        citiesList.add(new Cities("34","150800","巴彦淖尔市","150000"));
        citiesList.add(new Cities("35","150900","乌兰察布市","150000"));
        citiesList.add(new Cities("36","152200","兴安盟","150000"));
        citiesList.add(new Cities("37","152500","锡林郭勒盟","150000"));
        citiesList.add(new Cities("38","152900","阿拉善盟","150000"));
        citiesList.add(new Cities("39","210100","沈阳市","210000"));
        citiesList.add(new Cities("40","210200","大连市","210000"));
        citiesList.add(new Cities("41","210300","鞍山市","210000"));
        citiesList.add(new Cities("42","210400","抚顺市","210000"));
        citiesList.add(new Cities("43","210500","本溪市","210000"));
        citiesList.add(new Cities("44","210600","丹东市","210000"));
        citiesList.add(new Cities("45","210700","锦州市","210000"));
        citiesList.add(new Cities("46","210800","营口市","210000"));
        citiesList.add(new Cities("47","210900","阜新市","210000"));
        citiesList.add(new Cities("48","211000","辽阳市","210000"));
        citiesList.add(new Cities("49","211100","盘锦市","210000"));
        citiesList.add(new Cities("50","211200","铁岭市","210000"));
        citiesList.add(new Cities("51","211300","朝阳市","210000"));
        citiesList.add(new Cities("52","211400","葫芦岛市","210000"));
        citiesList.add(new Cities("53","220100","长春市","220000"));
        citiesList.add(new Cities("54","220200","吉林市","220000"));
        citiesList.add(new Cities("55","220300","四平市","220000"));
        citiesList.add(new Cities("56","220400","辽源市","220000"));
        citiesList.add(new Cities("57","220500","通化市","220000"));
        citiesList.add(new Cities("58","220600","白山市","220000"));
        citiesList.add(new Cities("59","220700","松原市","220000"));
        citiesList.add(new Cities("60","220800","白城市","220000"));
        citiesList.add(new Cities("61","222400","延边朝鲜族自治州","220000"));
        citiesList.add(new Cities("62","230100","哈尔滨市","230000"));
        citiesList.add(new Cities("63","230200","齐齐哈尔市","230000"));
        citiesList.add(new Cities("64","230300","鸡西市","230000"));
        citiesList.add(new Cities("65","230400","鹤岗市","230000"));
        citiesList.add(new Cities("66","230500","双鸭山市","230000"));
        citiesList.add(new Cities("67","230600","大庆市","230000"));
        citiesList.add(new Cities("68","230700","伊春市","230000"));
        citiesList.add(new Cities("69","230800","佳木斯市","230000"));
        citiesList.add(new Cities("70","230900","七台河市","230000"));
        citiesList.add(new Cities("71","231000","牡丹江市","230000"));
        citiesList.add(new Cities("72","231100","黑河市","230000"));
        citiesList.add(new Cities("73","231200","绥化市","230000"));
        citiesList.add(new Cities("74","232700","大兴安岭地区","230000"));
        citiesList.add(new Cities("75","310100","市辖区","310000"));
        citiesList.add(new Cities("76","310200","县","310000"));
        citiesList.add(new Cities("77","320100","南京市","320000"));
        citiesList.add(new Cities("78","320200","无锡市","320000"));
        citiesList.add(new Cities("79","320300","徐州市","320000"));
        citiesList.add(new Cities("80","320400","常州市","320000"));
        citiesList.add(new Cities("81","320500","苏州市","320000"));
        citiesList.add(new Cities("82","320600","南通市","320000"));
        citiesList.add(new Cities("83","320700","连云港市","320000"));
        citiesList.add(new Cities("84","320800","淮安市","320000"));
        citiesList.add(new Cities("85","320900","盐城市","320000"));
        citiesList.add(new Cities("86","321000","扬州市","320000"));
        citiesList.add(new Cities("87","321100","镇江市","320000"));
        citiesList.add(new Cities("88","321200","泰州市","320000"));
        citiesList.add(new Cities("89","321300","宿迁市","320000"));
        citiesList.add(new Cities("90","330100","杭州市","330000"));
        citiesList.add(new Cities("91","330200","宁波市","330000"));
        citiesList.add(new Cities("92","330300","温州市","330000"));
        citiesList.add(new Cities("93","330400","嘉兴市","330000"));
        citiesList.add(new Cities("94","330500","湖州市","330000"));
        citiesList.add(new Cities("95","330600","绍兴市","330000"));
        citiesList.add(new Cities("96","330700","金华市","330000"));
        citiesList.add(new Cities("97","330800","衢州市","330000"));
        citiesList.add(new Cities("98","330900","舟山市","330000"));
        citiesList.add(new Cities("99","331000","台州市","330000"));
        citiesList.add(new Cities("100","331100","丽水市","330000"));
        citiesList.add(new Cities("101","340100","合肥市","340000"));
        citiesList.add(new Cities("102","340200","芜湖市","340000"));
        citiesList.add(new Cities("103","340300","蚌埠市","340000"));
        citiesList.add(new Cities("104","340400","淮南市","340000"));
        citiesList.add(new Cities("105","340500","马鞍山市","340000"));
        citiesList.add(new Cities("106","340600","淮北市","340000"));
        citiesList.add(new Cities("107","340700","铜陵市","340000"));
        citiesList.add(new Cities("108","340800","安庆市","340000"));
        citiesList.add(new Cities("109","341000","黄山市","340000"));
        citiesList.add(new Cities("110","341100","滁州市","340000"));
        citiesList.add(new Cities("111","341200","阜阳市","340000"));
        citiesList.add(new Cities("112","341300","宿州市","340000"));
        citiesList.add(new Cities("113","341400","巢湖市","340000"));
        citiesList.add(new Cities("114","341500","六安市","340000"));
        citiesList.add(new Cities("115","341600","亳州市","340000"));
        citiesList.add(new Cities("116","341700","池州市","340000"));
        citiesList.add(new Cities("117","341800","宣城市","340000"));
        citiesList.add(new Cities("118","350100","福州市","350000"));
        citiesList.add(new Cities("119","350200","厦门市","350000"));
        citiesList.add(new Cities("120","350300","莆田市","350000"));
        citiesList.add(new Cities("121","350400","三明市","350000"));
        citiesList.add(new Cities("122","350500","泉州市","350000"));
        citiesList.add(new Cities("123","350600","漳州市","350000"));
        citiesList.add(new Cities("124","350700","南平市","350000"));
        citiesList.add(new Cities("125","350800","龙岩市","350000"));
        citiesList.add(new Cities("126","350900","宁德市","350000"));
        citiesList.add(new Cities("127","360100","南昌市","360000"));
        citiesList.add(new Cities("128","360200","景德镇市","360000"));
        citiesList.add(new Cities("129","360300","萍乡市","360000"));
        citiesList.add(new Cities("130","360400","九江市","360000"));
        citiesList.add(new Cities("131","360500","新余市","360000"));
        citiesList.add(new Cities("132","360600","鹰潭市","360000"));
        citiesList.add(new Cities("133","360700","赣州市","360000"));
        citiesList.add(new Cities("134","360800","吉安市","360000"));
        citiesList.add(new Cities("135","360900","宜春市","360000"));
        citiesList.add(new Cities("136","361000","抚州市","360000"));
        citiesList.add(new Cities("137","361100","上饶市","360000"));
        citiesList.add(new Cities("138","370100","济南市","370000"));
        citiesList.add(new Cities("139","370200","青岛市","370000"));
        citiesList.add(new Cities("140","370300","淄博市","370000"));
        citiesList.add(new Cities("141","370400","枣庄市","370000"));
        citiesList.add(new Cities("142","370500","东营市","370000"));
        citiesList.add(new Cities("143","370600","烟台市","370000"));
        citiesList.add(new Cities("144","370700","潍坊市","370000"));
        citiesList.add(new Cities("145","370800","济宁市","370000"));
        citiesList.add(new Cities("146","370900","泰安市","370000"));
        citiesList.add(new Cities("147","371000","威海市","370000"));
        citiesList.add(new Cities("148","371100","日照市","370000"));
        citiesList.add(new Cities("149","371200","莱芜市","370000"));
        citiesList.add(new Cities("150","371300","临沂市","370000"));
        citiesList.add(new Cities("151","371400","德州市","370000"));
        citiesList.add(new Cities("152","371500","聊城市","370000"));
        citiesList.add(new Cities("153","371600","滨州市","370000"));
        citiesList.add(new Cities("154","371700","荷泽市","370000"));
        citiesList.add(new Cities("155","410100","郑州市","410000"));
        citiesList.add(new Cities("156","410200","开封市","410000"));
        citiesList.add(new Cities("157","410300","洛阳市","410000"));
        citiesList.add(new Cities("158","410400","平顶山市","410000"));
        citiesList.add(new Cities("159","410500","安阳市","410000"));
        citiesList.add(new Cities("160","410600","鹤壁市","410000"));
        citiesList.add(new Cities("161","410700","新乡市","410000"));
        citiesList.add(new Cities("162","410800","焦作市","410000"));
        citiesList.add(new Cities("163","410900","濮阳市","410000"));
        citiesList.add(new Cities("164","411000","许昌市","410000"));
        citiesList.add(new Cities("165","411100","漯河市","410000"));
        citiesList.add(new Cities("166","411200","三门峡市","410000"));
        citiesList.add(new Cities("167","411300","南阳市","410000"));
        citiesList.add(new Cities("168","411400","商丘市","410000"));
        citiesList.add(new Cities("169","411500","信阳市","410000"));
        citiesList.add(new Cities("170","411600","周口市","410000"));
        citiesList.add(new Cities("171","411700","驻马店市","410000"));
        citiesList.add(new Cities("172","420100","武汉市","420000"));
        citiesList.add(new Cities("173","420200","黄石市","420000"));
        citiesList.add(new Cities("174","420300","十堰市","420000"));
        citiesList.add(new Cities("175","420500","宜昌市","420000"));
        citiesList.add(new Cities("176","420600","襄樊市","420000"));
        citiesList.add(new Cities("177","420700","鄂州市","420000"));
        citiesList.add(new Cities("178","420800","荆门市","420000"));
        citiesList.add(new Cities("179","420900","孝感市","420000"));
        citiesList.add(new Cities("180","421000","荆州市","420000"));
        citiesList.add(new Cities("181","421100","黄冈市","420000"));
        citiesList.add(new Cities("182","421200","咸宁市","420000"));
        citiesList.add(new Cities("183","421300","随州市","420000"));
        citiesList.add(new Cities("184","422800","恩施土家族苗族自治州","420000"));
        citiesList.add(new Cities("185","429000","省直辖行政单位","420000"));
        citiesList.add(new Cities("186","430100","长沙市","430000"));
        citiesList.add(new Cities("187","430200","株洲市","430000"));
        citiesList.add(new Cities("188","430300","湘潭市","430000"));
        citiesList.add(new Cities("189","430400","衡阳市","430000"));
        citiesList.add(new Cities("190","430500","邵阳市","430000"));
        citiesList.add(new Cities("191","430600","岳阳市","430000"));
        citiesList.add(new Cities("192","430700","常德市","430000"));
        citiesList.add(new Cities("193","430800","张家界市","430000"));
        citiesList.add(new Cities("194","430900","益阳市","430000"));
        citiesList.add(new Cities("195","431000","郴州市","430000"));
        citiesList.add(new Cities("196","431100","永州市","430000"));
        citiesList.add(new Cities("197","431200","怀化市","430000"));
        citiesList.add(new Cities("198","431300","娄底市","430000"));
        citiesList.add(new Cities("199","433100","湘西土家族苗族自治州","430000"));
        citiesList.add(new Cities("200","440100","广州市","440000"));
        citiesList.add(new Cities("201","440200","韶关市","440000"));
        citiesList.add(new Cities("202","440300","深圳市","440000"));
        citiesList.add(new Cities("203","440400","珠海市","440000"));
        citiesList.add(new Cities("204","440500","汕头市","440000"));
        citiesList.add(new Cities("205","440600","佛山市","440000"));
        citiesList.add(new Cities("206","440700","江门市","440000"));
        citiesList.add(new Cities("207","440800","湛江市","440000"));
        citiesList.add(new Cities("208","440900","茂名市","440000"));
        citiesList.add(new Cities("209","441200","肇庆市","440000"));
        citiesList.add(new Cities("210","441300","惠州市","440000"));
        citiesList.add(new Cities("211","441400","梅州市","440000"));
        citiesList.add(new Cities("212","441500","汕尾市","440000"));
        citiesList.add(new Cities("213","441600","河源市","440000"));
        citiesList.add(new Cities("214","441700","阳江市","440000"));
        citiesList.add(new Cities("215","441800","清远市","440000"));
        citiesList.add(new Cities("216","441900","东莞市","440000"));
        citiesList.add(new Cities("217","442000","中山市","440000"));
        citiesList.add(new Cities("218","445100","潮州市","440000"));
        citiesList.add(new Cities("219","445200","揭阳市","440000"));
        citiesList.add(new Cities("220","445300","云浮市","440000"));
        citiesList.add(new Cities("221","450100","南宁市","450000"));
        citiesList.add(new Cities("222","450200","柳州市","450000"));
        citiesList.add(new Cities("223","450300","桂林市","450000"));
        citiesList.add(new Cities("224","450400","梧州市","450000"));
        citiesList.add(new Cities("225","450500","北海市","450000"));
        citiesList.add(new Cities("226","450600","防城港市","450000"));
        citiesList.add(new Cities("227","450700","钦州市","450000"));
        citiesList.add(new Cities("228","450800","贵港市","450000"));
        citiesList.add(new Cities("229","450900","玉林市","450000"));
        citiesList.add(new Cities("230","451000","百色市","450000"));
        citiesList.add(new Cities("231","451100","贺州市","450000"));
        citiesList.add(new Cities("232","451200","河池市","450000"));
        citiesList.add(new Cities("233","451300","来宾市","450000"));
        citiesList.add(new Cities("234","451400","崇左市","450000"));
        citiesList.add(new Cities("235","460100","海口市","460000"));
        citiesList.add(new Cities("236","460200","三亚市","460000"));
        citiesList.add(new Cities("237","469000","省直辖县级行政单位","460000"));
        citiesList.add(new Cities("238","500100","市辖区","500000"));
        citiesList.add(new Cities("239","500200","县","500000"));
        citiesList.add(new Cities("240","500300","市","500000"));
        citiesList.add(new Cities("241","510100","成都市","510000"));
        citiesList.add(new Cities("242","510300","自贡市","510000"));
        citiesList.add(new Cities("243","510400","攀枝花市","510000"));
        citiesList.add(new Cities("244","510500","泸州市","510000"));
        citiesList.add(new Cities("245","510600","德阳市","510000"));
        citiesList.add(new Cities("246","510700","绵阳市","510000"));
        citiesList.add(new Cities("247","510800","广元市","510000"));
        citiesList.add(new Cities("248","510900","遂宁市","510000"));
        citiesList.add(new Cities("249","511000","内江市","510000"));
        citiesList.add(new Cities("250","511100","乐山市","510000"));
        citiesList.add(new Cities("251","511300","南充市","510000"));
        citiesList.add(new Cities("252","511400","眉山市","510000"));
        citiesList.add(new Cities("253","511500","宜宾市","510000"));
        citiesList.add(new Cities("254","511600","广安市","510000"));
        citiesList.add(new Cities("255","511700","达州市","510000"));
        citiesList.add(new Cities("256","511800","雅安市","510000"));
        citiesList.add(new Cities("257","511900","巴中市","510000"));
        citiesList.add(new Cities("258","512000","资阳市","510000"));
        citiesList.add(new Cities("259","513200","阿坝藏族羌族自治州","510000"));
        citiesList.add(new Cities("260","513300","甘孜藏族自治州","510000"));
        citiesList.add(new Cities("261","513400","凉山彝族自治州","510000"));
        citiesList.add(new Cities("262","520100","贵阳市","520000"));
        citiesList.add(new Cities("263","520200","六盘水市","520000"));
        citiesList.add(new Cities("264","520300","遵义市","520000"));
        citiesList.add(new Cities("265","520400","安顺市","520000"));
        citiesList.add(new Cities("266","522200","铜仁地区","520000"));
        citiesList.add(new Cities("267","522300","黔西南布依族苗族自治州","520000"));
        citiesList.add(new Cities("268","522400","毕节地区","520000"));
        citiesList.add(new Cities("269","522600","黔东南苗族侗族自治州","520000"));
        citiesList.add(new Cities("270","522700","黔南布依族苗族自治州","520000"));
        citiesList.add(new Cities("271","530100","昆明市","530000"));
        citiesList.add(new Cities("272","530300","曲靖市","530000"));
        citiesList.add(new Cities("273","530400","玉溪市","530000"));
        citiesList.add(new Cities("274","530500","保山市","530000"));
        citiesList.add(new Cities("275","530600","昭通市","530000"));
        citiesList.add(new Cities("276","530700","丽江市","530000"));
        citiesList.add(new Cities("277","530800","思茅市","530000"));
        citiesList.add(new Cities("278","530900","临沧市","530000"));
        citiesList.add(new Cities("279","532300","楚雄彝族自治州","530000"));
        citiesList.add(new Cities("280","532500","红河哈尼族彝族自治州","530000"));
        citiesList.add(new Cities("281","532600","文山壮族苗族自治州","530000"));
        citiesList.add(new Cities("282","532800","西双版纳傣族自治州","530000"));
        citiesList.add(new Cities("283","532900","大理白族自治州","530000"));
        citiesList.add(new Cities("284","533100","德宏傣族景颇族自治州","530000"));
        citiesList.add(new Cities("285","533300","怒江傈僳族自治州","530000"));
        citiesList.add(new Cities("286","533400","迪庆藏族自治州","530000"));
        citiesList.add(new Cities("287","540100","拉萨市","540000"));
        citiesList.add(new Cities("288","542100","昌都地区","540000"));
        citiesList.add(new Cities("289","542200","山南地区","540000"));
        citiesList.add(new Cities("290","542300","日喀则地区","540000"));
        citiesList.add(new Cities("291","542400","那曲地区","540000"));
        citiesList.add(new Cities("292","542500","阿里地区","540000"));
        citiesList.add(new Cities("293","542600","林芝地区","540000"));
        citiesList.add(new Cities("294","610100","西安市","610000"));
        citiesList.add(new Cities("295","610200","铜川市","610000"));
        citiesList.add(new Cities("296","610300","宝鸡市","610000"));
        citiesList.add(new Cities("297","610400","咸阳市","610000"));
        citiesList.add(new Cities("298","610500","渭南市","610000"));
        citiesList.add(new Cities("299","610600","延安市","610000"));
        citiesList.add(new Cities("300","610700","汉中市","610000"));
        citiesList.add(new Cities("301","610800","榆林市","610000"));
        citiesList.add(new Cities("302","610900","安康市","610000"));
        citiesList.add(new Cities("303","611000","商洛市","610000"));
        citiesList.add(new Cities("304","620100","兰州市","620000"));
        citiesList.add(new Cities("305","620200","嘉峪关市","620000"));
        citiesList.add(new Cities("306","620300","金昌市","620000"));
        citiesList.add(new Cities("307","620400","白银市","620000"));
        citiesList.add(new Cities("308","620500","天水市","620000"));
        citiesList.add(new Cities("309","620600","武威市","620000"));
        citiesList.add(new Cities("310","620700","张掖市","620000"));
        citiesList.add(new Cities("311","620800","平凉市","620000"));
        citiesList.add(new Cities("312","620900","酒泉市","620000"));
        citiesList.add(new Cities("313","621000","庆阳市","620000"));
        citiesList.add(new Cities("314","621100","定西市","620000"));
        citiesList.add(new Cities("315","621200","陇南市","620000"));
        citiesList.add(new Cities("316","622900","临夏回族自治州","620000"));
        citiesList.add(new Cities("317","623000","甘南藏族自治州","620000"));
        citiesList.add(new Cities("318","630100","西宁市","630000"));
        citiesList.add(new Cities("319","632100","海东地区","630000"));
        citiesList.add(new Cities("320","632200","海北藏族自治州","630000"));
        citiesList.add(new Cities("321","632300","黄南藏族自治州","630000"));
        citiesList.add(new Cities("322","632500","海南藏族自治州","630000"));
        citiesList.add(new Cities("323","632600","果洛藏族自治州","630000"));
        citiesList.add(new Cities("324","632700","玉树藏族自治州","630000"));
        citiesList.add(new Cities("325","632800","海西蒙古族藏族自治州","630000"));
        citiesList.add(new Cities("326","640100","银川市","640000"));
        citiesList.add(new Cities("327","640200","石嘴山市","640000"));
        citiesList.add(new Cities("328","640300","吴忠市","640000"));
        citiesList.add(new Cities("329","640400","固原市","640000"));
        citiesList.add(new Cities("330","640500","中卫市","640000"));
        citiesList.add(new Cities("331","650100","乌鲁木齐市","650000"));
        citiesList.add(new Cities("332","650200","克拉玛依市","650000"));
        citiesList.add(new Cities("333","652100","吐鲁番地区","650000"));
        citiesList.add(new Cities("334","652200","哈密地区","650000"));
        citiesList.add(new Cities("335","652300","昌吉回族自治州","650000"));
        citiesList.add(new Cities("336","652700","博尔塔拉蒙古自治州","650000"));
        citiesList.add(new Cities("337","652800","巴音郭楞蒙古自治州","650000"));
        citiesList.add(new Cities("338","652900","阿克苏地区","650000"));
        citiesList.add(new Cities("339","653000","克孜勒苏柯尔克孜自治州","650000"));
        citiesList.add(new Cities("340","653100","喀什地区","650000"));
        citiesList.add(new Cities("341","653200","和田地区","650000"));
        citiesList.add(new Cities("342","654000","伊犁哈萨克自治州","650000"));
        citiesList.add(new Cities("343","654200","塔城地区","650000"));
        citiesList.add(new Cities("344","654300","阿勒泰地区","650000"));
        citiesList.add(new Cities("345","659000","省直辖行政单位","650000"));

        citiesRepository.saveAll(citiesList);


    }





}

package com.state.snmp;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SnmpData {

    public static final int DEFAULT_VERSION= SnmpConstants.version2c;
    public static final String DEFAULT_PORTOCOL = "udp";
    public static final int DEFAULT_PORT=161;
    public static final long DEFAULT_TIMEOUT = 3*1000L;
    public static final int DEFAULT_RETRY = 3;


    public static CommunityTarget createDefault(String ip, String community){
        Address address = GenericAddress.parse(DEFAULT_PORTOCOL+":"+ip+"/"+DEFAULT_PORT);
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(community));
        target.setAddress(address);
        target.setVersion(DEFAULT_VERSION);
        target.setTimeout(DEFAULT_TIMEOUT);
        target.setRetries(DEFAULT_RETRY);
        return target;
    }
    /*根据OID，获取单条消息*/
    public static void snmpGet(String ip,String community, String oid){
        CommunityTarget target = createDefault(ip, community);
        Snmp snmp = null;
        try{
            PDU pdu = new PDU();
            pdu.add(new VariableBinding(new OID(oid)));

            DefaultUdpTransportMapping transportMapping = new DefaultUdpTransportMapping();
            snmp =  new Snmp(transportMapping);
            snmp.listen();

            System.out.print("---------------send pdu");
            pdu.setType(PDU.GET);
            ResponseEvent responseEvent = snmp.send(pdu, target);
            System.out.println("peer address: "+ responseEvent.getPeerAddress());
            PDU response = responseEvent.getResponse();

            if(response == null){
                System.out.println("response is null request time out");
            }else{
                System.out.println("response pdu size is "+ response.size());
                for (int i =0 ;i<response.size();i++){
                    VariableBinding vb = response.get(i);
                    System.out.println(vb.getOid() + " = " + vb.getVariable());
                }
            }
            System.out.println("SNMP GET on OID value finished!");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("SNMP get Exception"+ e);
        }finally {
            if(snmp!=null){
                try {
                    snmp.close();
                }catch (Exception ex ){
                    snmp=null;
                }
            }
        }
    }

    /**
     * 根据OID列表，一次获取多条OID数据， 并以list形式放回
     */
    public static void snmpGetList(String ip, String community, List<String> oidList){
        CommunityTarget target = createDefault(ip, community);
        Snmp snmp = null;
        try{
            PDU pdu = new PDU();
            for(String oid:oidList){
                pdu.add(new VariableBinding(new OID(oid)));
            }
            DefaultUdpTransportMapping transportMapping = new DefaultUdpTransportMapping();
            snmp = new Snmp(transportMapping);
            snmp.listen();
            System.out.println("-------- send pdu");
            pdu.setType(PDU.GET);
            ResponseEvent responseEvent = snmp.send(pdu, target);
            System.out.println("Peer address"+ responseEvent.getPeerAddress());
            PDU res = responseEvent.getResponse();
            if (res ==null){
                System.out.println("response is null, request time out");
            }else{
                System.out.println("response pud size is "+res.size());
                for (int i = 0;i<res.size();i++){
                    VariableBinding v = res.get(i);
                    System.out.println(v.getOid()+" = "+v.getVariable());
                }
            }
            System.out.println("Snmp get one oid value finished!");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("exception " + e);
        }finally {
            if(snmp!=null){
                try {
                    snmp.close();
                }catch (Exception ex ){
                    snmp=null;
                }
            }
        }
    }


    /**
     *  根据oid 列表， 采用异步方式一次获取多条oid数据， 并且以list方式返回
     */

    public static void snmpAsynGetList(String ip, String community, List<String> oidList){
        CommunityTarget target = createDefault(ip, community);
        Snmp snmp = null;
        try {
            PDU pdu =new PDU();
            for (String oid: oidList){
                pdu.add(new VariableBinding(new OID(oid)));
            }

            DefaultUdpTransportMapping transportMapping = new DefaultUdpTransportMapping();
            snmp= new Snmp(transportMapping);
            snmp.listen();
            System.out.println(" ----------- send pdu");
            pdu.setType(PDU.GET);
            ResponseEvent re = snmp.send(pdu, target);
            System.out.println("peer address: "+ re.getPeerAddress());
            PDU response = re.getResponse();

            //异步获取
            final CountDownLatch latch = new CountDownLatch(1);
            ResponseListener listener = new ResponseListener() {
                @Override
                public void onResponse(ResponseEvent responseEvent) {
                    ((Snmp) responseEvent.getSource()).cancel(responseEvent.getRequest(),this);
                    PDU response = responseEvent.getResponse();
                    PDU request = responseEvent.getRequest();
                    System.out.println("[request] : "+request);
                    if(response==null){
                        System.out.println("[ERROR]: resonse status "+response.getErrorStatus()
                        +" text: " +response.getErrorStatusText());
                    }else{
                        System.out.println("received response Success!");
                        for (int i = 0;i<response.size();i++){
                            VariableBinding vb = response.get(i);
                            System.out.println(vb.getOid()+" = "+vb.getVariable());
                        }
                        System.out.println("SNMP asyn getList OID finished ");
                        latch.countDown();
                    }
                }
            };
            pdu.setType(PDU.GET);
            snmp.send(pdu,target,null, listener);
            System.out.println("asyn send pdu wait for respnse....");
            boolean wait = latch.await(30, TimeUnit.SECONDS);
            System.out.println("latch.await =: "+wait);
            snmp.close();
            System.out.println("SNMP GET one OID value finished");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("SNMP get Exception :" +e);
        }finally {
            if (snmp!=null){
                try {
                    snmp.close();
                }catch (Exception ex){
                    snmp =null;
                }
            }
        }
    }

    /**
     *
     */
    public static void snmpWalk(String ip, String community, String targetOid){
        CommunityTarget target = createDefault(ip, community);
        TransportMapping transportMapping = null;
        Snmp snmp = null;
        try{
            transportMapping = new DefaultUdpTransportMapping();
            snmp = new Snmp(transportMapping);
            transportMapping.listen();

            PDU pdu = new PDU();
            OID targetOID = new OID(targetOid);
            pdu.add(new VariableBinding(targetOID));

            boolean finished = false;
            System.out.println("-------------demo start");
            while (!finished){
                VariableBinding vb = null;
                ResponseEvent responseEvent = snmp.getNext(pdu,target);
                PDU response = responseEvent.getResponse();

                if(null == response){
                    System.out.println("responsePDU == null");
                    finished= true;
                    break;
                }else{
                    vb = response.get(0);
                }
                finished = checkWalkFinished(targetOID,pdu,vb);


                if (!finished){
                    System.out.println("===== walk each value: ");
                    System.out.println(vb.getOid() + " = "+vb.getVariable());

                    //set up the variable binding for the next entry
                    pdu.setRequestID(new Integer32(0));
                    pdu.set(0, vb);
                }else{
                    System.out.println("SNMP walk OID has finished. ");
                    snmp.close();
                }
            }
            System.out.println("----------- demo end......");

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Snmp walk Exception :"+ e);
        }finally {
            if (snmp!=null){
                try{
                    snmp.close();
                }catch (Exception ex){
                    snmp=null;
                }
            }
        }
    }

    private static boolean checkWalkFinished(OID targetOID, PDU pdu, VariableBinding vb){
        boolean finished = false;
        if(pdu.getErrorStatus()!=0){
            System.out.println("[true] responsePDU. getErrorStatus() != 0");
            System.out.println(pdu.getErrorStatusText());
            finished= true;
        }else if(vb.getOid()==null){
            System.out.println("[true] vb.getOid() == null");
            finished = true;
        }else if(vb.getOid().size() <targetOID.size()){
            System.out.println("[true] vb.getOid().size() < targetOid.size() ");
            finished = true;
        }else if (targetOID.leftMostCompare(targetOID.size(), vb.getOid())!=0){
            System.out.println("targetOID.leftMostCompare(targetOID.size(), vb.getOid())!=0");
            finished = true;
        }else if(Null.isExceptionSyntax(vb.getVariable().getSyntax())){
            System.out.println("[true] Null.isExceptionSyntax(vb.getVariable().getSyntax())");
            finished=true;
        }else if(vb.getOid().compareTo(targetOID)<=0){
            System.out.println("[true] Variable received is not "
                    + "lexicographic successor of requested " + "one:");
            System.out.println(vb.toString()+ " <= "+targetOID);
            finished= true;
        }
        return finished;
    }

    public static void snmpAsyWalk(String ip, String community, String oid){
        final  CommunityTarget target= createDefault(ip, community);
        Snmp snmp = null;
        try {
            System.out.println("-------<demo start>------");
            DefaultUdpTransportMapping transportMapping = new DefaultUdpTransportMapping();
            snmp  = new Snmp(transportMapping);
            snmp.listen();

            final PDU pdu = new PDU();
            final OID targetOID = new OID(oid);
            final CountDownLatch latch = new CountDownLatch(1);
            pdu.add(new VariableBinding(targetOID));

            ResponseListener listener = new ResponseListener() {
                @Override
                public void onResponse(ResponseEvent responseEvent) {
                    ((Snmp) responseEvent.getSource()).cancel(responseEvent.getRequest(), this);
                    try{
                        PDU response = responseEvent.getResponse();
                        if(response==null){
                            System.out.println("[ERROR]: response is null");
                        }else if(response.getErrorStatus()!=0){
                            System.out.println("[ERROR]: response status "+response.getErrorStatus()
                            + " text :"+ response.getErrorStatusText());

                        }else {
                            System.out.println("received walk response value ");
                            VariableBinding vb = response.get(0);

                            boolean finished = checkWalkFinished(targetOID, pdu,vb);
                            if (!finished){
                                System.out.println(vb.getOid()+" = "+ vb.getVariable());
                                pdu.setRequestID(new Integer32(0));
                                pdu.set(0,vb);
                                ((Snmp) responseEvent.getSource()).getNext(pdu, target,null,this);

                            }else{
                                System.out.println("SNMP asyn walk OID value success!");
                                latch.countDown();
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        latch.countDown();
                    }
                }
            };

            snmp.getNext(pdu, target, null, listener);
            System.out.println("pdu has send, wait asyn result");

            boolean wait = latch.await(30, TimeUnit.SECONDS);
            System.out.println("latch.await = "+ wait);
            snmp.close();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("SNMP asyn walk exception "+e);
        }
    }

    public static void setPDU(String ip, String community, String oid, String val) throws IOException{
        CommunityTarget target = createDefault(ip, community);
        Snmp snmp =null;
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid), new OctetString(val)));
        pdu.setType(PDU.SET);

        DefaultUdpTransportMapping transportMapping = new DefaultUdpTransportMapping();
        snmp = new Snmp(transportMapping);
        snmp.listen();
        System.out.println("sending pdu....");
        snmp.send(pdu, target);
        snmp.close();
    }
}

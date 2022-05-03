package com.company;
import java.io.*;
import java.util.*;

class Nod {
    public Nod stanga;
    public Nod dreapta;
    public int index;
    public String valoare;

    Nod(String valoare, int index){
        this.valoare = valoare;
        this.stanga = null;
        this.dreapta = null;
        this.index = index;
    }
}

class Aux{
    public int index;
    public String valoare;
    public int parinte;
    public int pozitie;
}

class FileParser{
    private String fisier;
    static String FILE_PATH  =  "/home/school/IdeaProjects/untitled/out/production/untitled/com/company/";

    FileParser(String fisier){
        this.setFisier(fisier);
    }

    FileParser(){
    }

    public void setFisier(String fisier) {
        this.fisier = fisier;
    }

    private List<Aux> readLines(){
        try{
            BufferedReader readerr = new BufferedReader(
                    new InputStreamReader(System.in));

            String fisier = readerr.readLine();
            File file = new File(this.FILE_PATH + fisier);
            Scanner reader = new Scanner(file);
            List set = new ArrayList<Aux>();
            Boolean radacina = false;
            String data;
            while(reader.hasNextLine()){
                data = reader.nextLine();
                List<String> datelist = Arrays.asList(data.split(","));
                Aux aux =new Aux();
                if(datelist.size()!=4){
                    throw new Exception("Nodul are date insuficiente");
                }
                aux.index = Integer.parseInt((String)datelist.get(0));
                aux.parinte = Integer.parseInt((String)datelist.get(1));
                aux.pozitie = Integer.parseInt((String)datelist.get(2));
                aux.valoare = datelist.get(3);
                if(aux.parinte < 0){
                    if(!radacina)
                        radacina = true;
                    else
                        throw new Exception("Arborele nu poate avea 2 radacini");
                }
                set.add(aux);
                System.out.println(data);
            }
            reader.close();
            if(!radacina){
                throw new Exception("Arborele trebuie sa aiba o radacina");
            }
            return set;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private HashMap<Integer, Nod> listToNodeHashmap(List<Aux> list){
        HashMap<Integer,Nod> map = new HashMap<Integer,Nod>();
        for(int i = 0; i< list.size(); i++) {
            Aux item= list.get(i);
            Nod nod = new Nod(item.valoare,item.index);
            System.out.println("Se initializeaza nodul "+nod.index);
            map.put(item.index,nod);
        }
        return map;
    }

    public ArboreBinar genereazaArbore() throws Exception{
        List<Aux> list = this.readLines();
        ArboreBinar arbore = new ArboreBinar();
        HashMap<Integer, Nod> map = this.listToNodeHashmap(list);
        list.forEach((item) -> {
            if(item.parinte<0){
                arbore.radacina = map.get(item.index);
            }else{
                Nod nod = map.get(item.index);
                Nod parinte = map.get(item.parinte);
                if(item.pozitie == 0){
                    parinte.stanga = nod;
                }else {
                    if (item.pozitie == 1) {
                        parinte.dreapta = nod;
                    } else {
                        try {
                            throw new Exception("Parintele contine nodul");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        });
        System.out.println("Radacina are indexul "+arbore.radacina.index);
        return arbore;
    }
}

class ArboreBinar{
    public Nod radacina;

    public void parcurgere(){
        Queue<Nod> queue = new ArrayDeque<>();
        queue.add(radacina);
        while(!queue.isEmpty()) {
            Nod currentNode = queue.remove();
            System.out.print(currentNode.index + " ");
            if (currentNode.stanga != null)
                queue.add(currentNode.stanga);
            if (currentNode.dreapta != null)
                queue.add(currentNode.dreapta);
        }
    }
}

public class Main {

    public static void main(String[] args) {
	    FileParser parser = new FileParser("fisier");

        try{
            ArboreBinar arbore = parser.genereazaArbore();
            arbore.parcurgere();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }




    }
}

/*
* Nr. nodului | Nr. parinte | Pozitie | Valoare
* 1,-1,-1,abc
* 2,1,0,afd
* 3,1,1,asad
* 4,3,1,aaa
*
*
* */
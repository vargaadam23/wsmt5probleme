<?php
class Aux{
    public $index;
    public $valoare;
    public $parinte;
    public $pozitie;

    function __construct($v,$i,$p,$poz){
        $this->valoare = $v;
        $this->index = $i;
        $this->parinte = $p;
        $this->pozitie = $poz;
    }
}
class Nod{
    public $stanga;
    public $dreapta;
    public $index;
    public $valoare;

    function __construct($valoare, $index){
        $this->valoare = $valoare;
        $this->index = $index;
    }
}
class FileParser{
    public const FILE_PATH = "/home/school/Projects/5programe/";

    function readLines(){
        $fisier = readline('Enter a string: ');
        print(self::FILE_PATH.$fisier);
        $file = fopen(self::FILE_PATH.$fisier,"r");
        $set = array();
        $radacina = false;
        if($file){
            while (($line = fgets($file)) !== false) {
                $dateList = explode(",",$line);
                if(sizeof($dateList)!=4){
                    throw new Exception("Nodul are date insuficiente");
                }
                $aux = new Aux($dateList[3],$dateList[0],$dateList[1],$dateList[2]);
                if($aux->parinte < 0){
                    if(!$radacina){
                        $radacina = true;
                    }else{
                        throw new Exception("Arborele nu poate avea 2 radacini");
                    }
                }
                array_push($set,$aux);

            }
            fclose($file);
            if(!$radacina){
                throw new Exception("Arborele trebuie sa aiba o radacina");
            }
            return $set;
        }

    }

    function listNodeToHashmap($list){
        $map = array();
        foreach($list as $item){
            $nod = new Nod($item->valoare,$item->index);
            $map[$item->index] = $nod;
        }
        return $map;
    }

    function genereazaArbore(){
        $list = $this->readLines();
        $arbore = new ArboreBinar();
        $map = $this->listNodeToHashmap($list);
        foreach($list as $item){
            if($item->parinte < 0){
                $arbore->radacina = $map[$item->index];

            }else{
                $nod = $map[$item->index];
                $parinte = $map[$item->parinte];
                if($item->pozitie == 0){
                    $parinte->stanga = $nod;
                }else {
                    if ($item->pozitie == 1) {
                        $parinte->dreapta = $nod;
                    } else {
                            throw new Exception("Parintele contine nodul");
                    }
                }
            }
        }
        return $arbore;
    }
}
class ArboreBinar{
    public $radacina;

    function parcurgere(){
        $queue = new SplQueue();
        $queue->enqueue($this->radacina);
        while(sizeof($queue)>0) {
            $currentNode = $queue->dequeue();
            print($currentNode->index." ");
            if ($currentNode->stanga != null)
                $queue->enqueue($currentNode->stanga);
            if ($currentNode->dreapta != null)
                $queue->enqueue($currentNode->dreapta);
        }
    }
}

$fileParser = new FileParser();
$arbore = $fileParser->genereazaArbore();
$arbore->parcurgere();

?>
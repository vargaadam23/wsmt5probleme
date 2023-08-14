const { FILE } = require('dns');

class Nod {
    constructor(index, valoare) {
        this.index = index;
        this.valoare = valoare;
        this.stanga = null;
        this.dreapta = null;
    }
}
class Aux {
    constructor(index, valoare, parinte, pozitie) {
        this.index = index;
        this.valoare = valoare;
        this.parinte = parinte;
        this.pozitie = pozitie;
    }
}
class FileParser {
    FILE_PATH = "/home/school/Projects/wsmt5probleme/";
    constructor() {

    }
    readLines() {
        // const readline = require('readline').createInterface({
        //     input: process.stdin,
        //     output: process.stdout
        // });
        // let fileName;
        // readline.question('Dati numele fisierului: ', name => {
        //     console.log(`Hey there ${name}!`);
        //     fileName = name;
        //     readline.close();
        // });
        let fileName = "fisier";
        const fs = require('fs');

        const set = [];
        let radacina = false;

        const allFileContents = fs.readFileSync(this.FILE_PATH + fileName, 'utf-8');
        allFileContents.split(/\r?\n/).forEach(line => {
            const dateList = line.split(",");
            const aux = new Aux(dateList[0], dateList[3], dateList[1], dateList[2]);
            if (dateList.length != 4) {
                throw "Date insuficiente";
            }
            if (aux.parinte < 0) {
                if (!radacina) {
                    radacina = true;
                } else {
                    throw "Arborele nu poate avea 2 radacini";
                }
            }
            set.push(aux);

        });
        if (!radacina) {
            throw "Arborele trebuie sa aiba o radacina";
        }
        return set;
    }

    listToNodeHashmap(list) {
        const map = {};
        for (let i = 0; i < list.length; i++) {
            const item = list[i];
            const nod = new Nod(item.index, item.valoare);
            map[item.index] = nod;
        }
        return map;
    }

    genereazaArbore() {
        const list = this.readLines();
        const arbore = new ArboreBinar();
        const map = this.listToNodeHashmap(list);
        list.forEach(item => {
            if (item.parinte < 0) {
                arbore.radacina = map[item.index];
            } else {
                const nod = map[item.index];
                const parinte = map[item.parinte];
                if (item.pozitie == 0) {
                    parinte.stanga = nod;
                } else {
                    if (item.pozitie == 1) {
                        parinte.dreapta = nod;
                    } else {
                        throw "Parintele constine nodul";
                    }
                }
            }
        });
        return arbore;
    }

}
class ArboreBinar {
    parcurgere() {
        const queue = new Queue();
        queue.enqueue(this.radacina);
        while (!queue.isEmpty()) {
            const current = queue.dequeue();
            console.log(current.index + " ");
            if (current.stanga) {
                queue.enqueue(current.stanga);
            }
            if (current.dreapta) {
                queue.enqueue(current.dreapta);
            }
        }
    }
}

class Queue {
    constructor() {
        this.elements = {};
        this.head = 0;
        this.tail = 0;
    }
    enqueue(element) {
        this.elements[this.tail] = element;
        this.tail++;
    }
    dequeue() {
        const item = this.elements[this.head];
        delete this.elements[this.head];
        this.head++;
        return item;
    }
    get length() {
        return this.tail - this.head;
    }
    isEmpty() {
        return this.length === 0;
    }
}

const file = new FileParser();
const arbore = file.genereazaArbore();
arbore.parcurgere();
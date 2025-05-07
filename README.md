# Árboles Binarios - Print Jerárquico en Consola

[![d2UNFje.md.png](https://iili.io/d2UNFje.md.png)](https://freeimage.host/i/d2UNFje)

Dejare este pequeño aporte para todos aquellos que estan aprendiendo sobre árboles binarios.  
Son dos archivos que le permitirán imprimir un árbol binario de forma jerárquica en consola.

## Primero

En su clase Node debe considerar lo sig:
1. Debe agregar los sigs campos
```
   private String id; // solo para el print del docente
   private boolean viewed; // solo para el print del docente
```
2. En el constructor agregar la generacion del id
```
   public Node(int value) {
        this.value = value;
        this.id = UUID.randomUUID().toString(); // agregar esta linea
    }
```
3. Debe tener las siguientes funciones:  
   getLeft, getRight, getValue, getId, isViewed, setViewed  
   isLeaf, hasTwoSon, getChildren (copiar de clase Node de este proyecto)

## Segundo

1. Adicionar a su proyecto las clases: TBPrint y TBPrintUtil
2. Arreglar los imports de dependencias en las clases TBPrint y TBPrintUtil
3. En su clase árbol implementar la interfaz TBPrint, el cual le obligará implementar el método getRoot, si ya tiene dicho método ya no se lo pedirá.

## Tercero

En algun método main agregar algo parecido a lo siguiente:
```
   public static void main(String[] args) {
       TreeBinary tb = new TreeBinary(); // clase de su árbol binario
       //... llamar a algun metodo que cargue datos
       TBPrintUtil.print(tb); // el print acepta cualquier clase que implemente la interfaz TBPrint
   }
```

# Árboles N-ários - Print Jerárquico en Consola

[![3ef6Sxj.md.png](https://iili.io/3ef6Sxj.md.png)](https://freeimage.host/i/3ef6Sxj)

Dejare este pequeño aporte para todos aquellos que estan aprendiendo sobre árboles n-arios.  
Son dos archivos que le permitirán imprimir un árbol n-ario de forma jerárquica en consola.

## Primero

En su clase Node debe considerar lo sig:
1. Debe agregar los sigs campos
```
   private String id; // solo para el print del docente
   private boolean viewed; // solo para el print del docente
```
2. En el constructor agregar la generacion del id
```
   public Node(int value) {
        this.value = value;
        this.id = UUID.randomUUID().toString(); // agregar esta linea
    }
```
3. Debe tener las siguientes funciones:  
   getValue, getId, isViewed, setViewed, getChildren 
   isLeaf (copiar de clase Node de este proyecto)

## Segundo

1. Adicionar a su proyecto las clases: TNPrint y TNPrintUtil
2. Arreglar los imports de dependencias en las clases TNPrint y TNPrintUtil
3. En su clase árbol n-ario implementar la interfaz TNPrint, el cual le obligará implementar el método getRoot, si ya tiene dicho método ya no se lo pedirá.

## Tercero

En algun método main agregar algo parecido a lo siguiente:
```
   public static void main(String[] args) {
       NarioTree t = new NarioTree(); // clase de su árbol n-ario
       //... llamar a algun metodo que cargue datos
       TNPrintUtil.print(t); // el print acepta cualquier clase que implemente la interfaz TNPrint
   }
```

## Recomendaciones
Canal Youtube: [MathLogic - Haciendo Fácil Lo Difícil](https://www.youtube.com/@mathlogic-haciendofacillod7053)

PlayList curso completo de árboles:  
https://www.youtube.com/playlist?list=PLJeMuvKPxpu2UGbpuFrcEAPCYhZzdCpBk

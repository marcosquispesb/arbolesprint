# Print Jerarquico de Árboles Binarios en Consola

[![d2UNFje.md.png](https://iili.io/d2UNFje.md.png)](https://freeimage.host/i/d2UNFje)

Dejare este pequeño aporte para todos aquellos que estan aprendiendo sobre arboles binarios.  
Son dos arvhivos que permitiran que puedan imprimir un arbol de forma jerarquica en consola.

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
3. En su clase arbol implementar la interfaz TBPrint, el cual le obligará implementar el metodo getRoot, si ya tiene dicho metodo genial.

## Tercero

En algun metodo main agregar algo parecido a lo siguiente
```
   public static void main(String[] args) {
       TreeBinary tb = new TreeBinary(); // clase de su arbol binario
       //... llamar a algun metodo que cargue datos
       TBPrintUtil.print(tb); // el print acepta cualquier clase que implemente la interfaz TBPrint
   }
```

## Recomendaciones
Canal Youtube: [MathLogic - Haciendo Fácil Lo Difícil](https://www.youtube.com/@mathlogic-haciendofacillod7053)

PlayList curso completo de árboles:  
https://www.youtube.com/playlist?list=PLJeMuvKPxpu2UGbpuFrcEAPCYhZzdCpBk

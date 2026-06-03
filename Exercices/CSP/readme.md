# CSP y Backtracking

## Introducción práctica con 8 Reinas y Sudoku

### Programación Competitiva

---

# Objetivos

Al finalizar esta sesión el estudiante deberá ser capaz de:

* Comprender qué es un CSP (Constraint Satisfaction Problem).
* Comprender la estrategia de Backtracking.
* Identificar variables, dominios y restricciones.
* Resolver problemas clásicos mediante búsqueda con retroceso.
* Implementar soluciones en C (ANSI) y Python.

---

# 1. ¿Qué es un CSP?

Un **Constraint Satisfaction Problem (CSP)** es un problema compuesto por:

## Variables

Elementos que deben recibir un valor.

Ejemplos:

* Reina de la fila 0
* Reina de la fila 1
* Celda (2,3) de un Sudoku

---

## Dominio

Conjunto de valores permitidos para una variable.

Ejemplos:

* Columnas 0..7 para una reina.
* Números 1..9 para una celda de Sudoku.

---

## Restricciones

Reglas que deben cumplirse.

Ejemplos:

* Dos reinas no pueden atacarse.
* Dos celdas de una misma fila no pueden tener el mismo número.

---

# 2. ¿Qué es Backtracking?

Backtracking es una técnica de búsqueda basada en:

1. Construir una solución paso a paso.
2. Verificar si sigue siendo válida.
3. Continuar si es válida.
4. Retroceder cuando aparece una contradicción.

---

## Idea intuitiva

Imaginemos un laberinto.

Cuando encontramos un camino:

* avanzamos.

Cuando llegamos a un callejón sin salida:

* regresamos al último cruce.
* intentamos otro camino.

Ese regreso es el **backtrack**.

---

# Esquema general

```text
resolver(estado):

    si estado es solución:
        retornar éxito

    para cada opción posible:

        si opción válida:

            aplicar opción

            si resolver():
                retornar éxito

            deshacer opción

    retornar fracaso
```

---

# Componentes de una solución Backtracking

Todo problema suele tener:

## Función de validación

Determina si una decisión cumple las restricciones.

```text
es_valido(...)
```

---

## Función recursiva

Explora el espacio de búsqueda.

```text
resolver(...)
```

---

## Caso base

La solución está completa.

```text
si ya resolvimos todo:
    éxito
```

---

## Backtrack

Deshacer una decisión.

```text
estado = anterior
```

---

# 3. Problema de las 8 Reinas

## Enunciado

Colocar 8 reinas en un tablero de ajedrez de forma que ninguna pueda atacar a otra.

---

## Restricciones

Dos reinas no pueden compartir:

* fila
* columna
* diagonal principal
* diagonal secundaria

---

# Modelado CSP

## Variables

Cada fila del tablero.

```text
Fila 0
Fila 1
...
Fila 7
```

---

## Dominio

Columnas posibles.

```text
0..7
```

---

## Restricciones

Si existe una reina en:

```text
(fila1,col1)
```

y otra en:

```text
(fila2,col2)
```

entonces:

```text
col1 != col2
```

y

```text
abs(col1-col2) != abs(fila1-fila2)
```

---

# Estrategia paso a paso

## Paso 1

Colocar reina en fila 0.

```text
Q . . . . . . .
```

---

## Paso 2

Intentar colocar reina en fila 1.

Descartar posiciones atacadas.

```text
Q . . . . . . .
. . Q . . . . .
```

---

## Paso 3

Intentar fila 2.

---

## Paso 4

Si ninguna columna sirve:

```text
Backtrack
```

Eliminar reina anterior.

Probar otra columna.

---

## Paso 5

Continuar hasta completar las 8 filas.

---

# Solución en Python

```python
N = 8

def es_seguro(tablero, fila, col):

    for i in range(fila):

        if tablero[i] == col:
            return False

        if abs(tablero[i] - col) == abs(i - fila):
            return False

    return True


def resolver(tablero, fila):

    if fila == N:
        return True

    for col in range(N):

        if es_seguro(tablero, fila, col):

            tablero[fila] = col

            if resolver(tablero, fila + 1):
                return True

            tablero[fila] = -1

    return False


tablero = [-1] * N

if resolver(tablero, 0):
    print(tablero)
```

---

# Explicación

## tablero[fila]

Guarda la columna donde está la reina.

Ejemplo:

```text
[0,4,7,5,2,6,1,3]
```

significa:

```text
Fila 0 -> Columna 0
Fila 1 -> Columna 4
Fila 2 -> Columna 7
...
```

---

## es_seguro()

Verifica:

* columnas repetidas
* diagonales repetidas

---

## resolver()

Prueba todas las columnas posibles.

Si una decisión falla:

```python
tablero[fila] = -1
```

Se deshace la decisión.

---

# Solución ANSI C

```c
#include <stdio.h>
#include <stdlib.h>

#define N 8

int tablero[N];

int seguro(int fila, int col){

    int i;

    for(i=0;i<fila;i++){

        if(tablero[i] == col)
            return 0;

        if(abs(tablero[i]-col) == abs(i-fila))
            return 0;
    }

    return 1;
}

int resolver(int fila){

    int col;

    if(fila == N)
        return 1;

    for(col=0; col<N; col++){

        if(seguro(fila,col)){

            tablero[fila] = col;

            if(resolver(fila+1))
                return 1;

            tablero[fila] = -1;
        }
    }

    return 0;
}

int main(){

    int i;

    for(i=0;i<N;i++)
        tablero[i] = -1;

    if(resolver(0)){

        for(i=0;i<N;i++)
            printf("%d ", tablero[i]);

        printf("\n");
    }

    return 0;
}
```

---

# 4. Sudoku como CSP

## Enunciado

Completar el tablero respetando:

* filas
* columnas
* subcuadrículas

---

# Sudoku 4x4

```text
1 . . 4
. . 1 .
. 1 . .
4 . . 2
```

---

# Modelado CSP

## Variables

Cada celda vacía.

---

## Dominio

```text
1
2
3
4
```

---

## Restricciones

No repetir números en:

* fila
* columna
* bloque 2x2

---

# Estrategia paso a paso

## Buscar una celda vacía

Ejemplo:

```text
(0,1)
```

---

## Probar valores

```text
1 -> inválido
2 -> válido
3 -> válido
4 -> inválido
```

---

## Elegir uno

```text
1 2 . 4
```

---

## Continuar

Resolver siguiente celda.

---

## Si ninguna opción funciona

```text
Backtrack
```

Borrar número.

Probar otro.

---

# Solución Python

```python
N = 4

tablero = [
 [1,0,0,4],
 [0,0,1,0],
 [0,1,0,0],
 [4,0,0,2]
]


def seguro(fila, col, num):

    for i in range(N):

        if tablero[fila][i] == num:
            return False

        if tablero[i][col] == num:
            return False

    inicio_fila = (fila // 2) * 2
    inicio_col = (col // 2) * 2

    for i in range(2):
        for j in range(2):

            if tablero[inicio_fila+i][inicio_col+j] == num:
                return False

    return True


def resolver():

    for fila in range(N):
        for col in range(N):

            if tablero[fila][col] == 0:

                for num in range(1, N+1):

                    if seguro(fila,col,num):

                        tablero[fila][col] = num

                        if resolver():
                            return True

                        tablero[fila][col] = 0

                return False

    return True


resolver()

for fila in tablero:
    print(fila)
```

---

# Solución ANSI C

```c
#include <stdio.h>

#define N 4

int tablero[N][N] = {
    {1,0,0,4},
    {0,0,1,0},
    {0,1,0,0},
    {4,0,0,2}
};

int seguro(int fila, int col, int num){

    int i,j;

    for(i=0;i<N;i++){

        if(tablero[fila][i] == num)
            return 0;

        if(tablero[i][col] == num)
            return 0;
    }

    int inicioFila = (fila/2)*2;
    int inicioCol = (col/2)*2;

    for(i=0;i<2;i++){
        for(j=0;j<2;j++){

            if(tablero[inicioFila+i][inicioCol+j] == num)
                return 0;
        }
    }

    return 1;
}

int resolver(){

    int fila,col,num;

    for(fila=0;fila<N;fila++){

        for(col=0;col<N;col++){

            if(tablero[fila][col] == 0){

                for(num=1; num<=N; num++){

                    if(seguro(fila,col,num)){

                        tablero[fila][col] = num;

                        if(resolver())
                            return 1;

                        tablero[fila][col] = 0;
                    }
                }

                return 0;
            }
        }
    }

    return 1;
}

int main(){

    int i,j;

    resolver();

    for(i=0;i<N;i++){

        for(j=0;j<N;j++)
            printf("%d ", tablero[i][j]);

        printf("\n");
    }

    return 0;
}
```

---

# Comparación final

| Problema | Variable    | Dominio  | Restricción |
| -------- | ----------- | -------- | ----------- |
| 8 Reinas | Fila        | Columnas | No atacarse |
| Sudoku   | Celda vacía | Números  | No repetir  |

---

# Idea clave

Aunque los problemas parecen distintos, ambos utilizan exactamente el mismo patrón:

```text
1. Elegir una variable.
2. Probar un valor.
3. Verificar restricciones.
4. Continuar recursivamente.
5. Si falla, deshacer.
6. Probar otra alternativa.
```

Ese patrón es la esencia del Backtracking y constituye la base de una gran cantidad de problemas de programación competitiva, inteligencia artificial y satisfacción de restricciones.

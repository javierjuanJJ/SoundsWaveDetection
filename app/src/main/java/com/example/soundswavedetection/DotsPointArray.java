package com.example.soundswavedetection;

class DotsPointArray {


    final float[] bufferArray;
    final int bufferSize, numValuesPerPoint,numValuesPerElement, numElements;
    int currPos;

    public DotsPointArray(int numElements, int numValuesPerElement){
        this(numElements,numValuesPerElement,2);
    }

    public DotsPointArray(int numElements, int numValuesPerElement, int numElementsPerPoint) {
        this.currPos = 0;
        this.numElements = numElements;
        this.numValuesPerElement = numValuesPerElement;
        this.numValuesPerPoint = numElementsPerPoint;
        this.bufferSize = numElements * numValuesPerElement * numElementsPerPoint;
        this.bufferArray = new float[this.bufferSize];
    }

    public boolean add(float... args){
        int numInputVal = args.length;

        if (numInputVal != numValuesPerElement * numValuesPerPoint){
            return false;
        }
        for (int i = 0; i < numInputVal; i++) {
            bufferArray[(currPos + i) % bufferSize] = args[i];
        }
        currPos = (currPos + numInputVal) % bufferSize;
        return  true;
    }

    public float[] getArray(){
        return this.getIndexedArray(0);
    }

    float[] getIndexedArray(int startIndex) {
        float[] onBuff = new float[this.bufferSize];
        int actualPosition = 0;

        for (int i = 0; i < numElements; i++) {
            for (int j = 0; j < numValuesPerElement; j++) {
                actualPosition = i * numValuesPerPoint * numValuesPerElement + j * numValuesPerElement;
                onBuff[actualPosition] = i + startIndex;
                for (int k = 0; k < numValuesPerPoint; k++) {
                    actualPosition++;
                    onBuff[actualPosition] = bufferArray[(currPos + actualPosition)];
                }
            }
        }

        return onBuff;
    }

}

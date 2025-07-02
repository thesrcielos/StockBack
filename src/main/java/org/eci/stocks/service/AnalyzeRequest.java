package org.eci.stocks.service;

public class AnalyzeRequest {
    private Object object1;
    private Object object2;

    public AnalyzeRequest(Object object1, Object object2) {
        this.object1 = object1;
        this.object2 = object2;
    }

    public Object getObject1() {
        return object1;
    }

    public void setObject1(Object object1) {
        this.object1 = object1;
    }

    public Object getObject2() {
        return object2;
    }

    public void setObject2(Object object2) {
        this.object2 = object2;
    }
}

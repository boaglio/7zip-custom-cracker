package com.boaglio.zipcracker;

public enum Symbols {
    nothing(""), a("!"), b("@"), c("#"), d("$"), e("%"), f("^"), g("&"), h("*"), i("("), j(")");

    Symbols(String v) {
        this.value = v;
    }

    private String value;

    public String value() {
        return this.value;
    }
}

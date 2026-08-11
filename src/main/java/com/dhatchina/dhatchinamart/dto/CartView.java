package com.dhatchina.dhatchinamart.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartView {

    private final List<CartLine> lines = new ArrayList<>();

    public void addLine(CartLine line) {
        lines.add(line);
    }

    public List<CartLine> getLines() {
        return lines;
    }

    public boolean isEmpty() {
        return lines.isEmpty();
    }

    public int getCount() {
        return lines.stream().mapToInt(CartLine::getQuantity).sum();
    }

    public BigDecimal getTotal() {
        return lines.stream()
                .map(CartLine::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

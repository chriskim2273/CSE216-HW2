package arithmetic;

public class FiniteGroupOfOrderTwo implements Group<PlusOrMinusOne>{
    @Override
    public PlusOrMinusOne binaryOperation(PlusOrMinusOne one, PlusOrMinusOne other) {
        return new PlusOrMinusOne(one.getValue() * other.getValue());
    }

    @Override
    public PlusOrMinusOne identity() {
        return PlusOrMinusOne.values()[1];
    }

    @Override
    public PlusOrMinusOne inverseOf(PlusOrMinusOne plusOrMinusOne) {
        PlusOrMinusOne[] vs = PlusOrMinusOne.values();
        if(plusOrMinusOne.getValue() == vs[0].getValue())
            return vs[1];
        else
            return vs[0];
    }
}

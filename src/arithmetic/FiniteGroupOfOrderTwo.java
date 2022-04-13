package arithmetic;

public class FiniteGroupOfOrderTwo implements Group<PlusOrMinusOne>{
    @Override
    public PlusOrMinusOne binaryOperation(PlusOrMinusOne one, PlusOrMinusOne other) {
        return new PlusOrMinusOne(Integer.parseInt(one.toString()) * Integer.parseInt(other.toString()));
    }

    @Override
    public PlusOrMinusOne identity() {
        return PlusOrMinusOne.values()[1];
    }

    @Override
    public PlusOrMinusOne inverseOf(PlusOrMinusOne plusOrMinusOne) {
        PlusOrMinusOne[] vs = PlusOrMinusOne.values();
        if(Integer.parseInt(plusOrMinusOne.toString()) == Integer.parseInt(vs[0].toString()))
            return vs[1];
        else
            return vs[0];
    }
}

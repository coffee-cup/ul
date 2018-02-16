package AST;

public class Identifier extends Expression {
    private String name;

    public Identifier(String name, int line, int offset) {
        this.setName(name);
        this.line = line;
        this.offset = offset;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof Identifier) {
            return ((Identifier) obj).getName().equals(this.getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }
}

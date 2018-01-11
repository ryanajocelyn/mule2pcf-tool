package freemarker.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Dependencies
{
    private Dependency[] dependency;

    public Dependency[] getDependency ()
    {
        return dependency;
    }

    public void setDependency (Dependency[] dependency)
    {
        this.dependency = dependency;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [dependency = "+dependency+"]";
    }
}


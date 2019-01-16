package gui.html;

import gui.api.HTMLVisualization;
import play.twirl.api.Html;

import views.html.renders.html.navbar;

public class Navbar implements HTMLVisualization {

    public Navbar() {}

    @Override
    public Html render() {
        return navbar.render(this);
    }
}

package gui.html;

import play.twirl.api.Html;
import gui.api.HTMLVisualization;

import views.html.renders.html.page;

import java.util.Optional;

public class Page implements HTMLVisualization {

    private HTMLVisualization navbar;

    final private HTMLVisualization content;

    public Page(final HTMLVisualization content) {
        this.content = content;
    }

    public Page(final HTMLVisualization content, final HTMLVisualization navbar) {
        this.content = content;
        this.navbar = navbar;
    }

    public HTMLVisualization getContent() {
        return content;
    }

    public Optional<HTMLVisualization> getNavbar() {
        return Optional.ofNullable(navbar);
    }

    public void setNavbar(final HTMLVisualization navbar) {
        this.navbar = navbar;
    }

    @Override
    public Html render() {
        return page.render(this);
    }
}

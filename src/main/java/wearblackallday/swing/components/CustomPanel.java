package wearblackallday.swing.components;

import wearblackallday.swing.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class CustomPanel extends JPanel {

    private final Map<String, JTextField> textFields = new HashMap<>();
    private final Map<Key<?>, Component> components = new HashMap<>();
    private final int defaultWidth, defaultHeight;

    public CustomPanel(LayoutManager layoutManager, int defaultWidth, int defaultHeight) {
        this.setLayout(layoutManager);
        this.defaultWidth = defaultWidth;
        this.defaultHeight = defaultHeight;
    }

    public CustomPanel(LayoutManager layoutManager) {
        this(layoutManager, 0, 0);
    }

    public CustomPanel(int defaultWidth, int defaultHeight) {
        this(new FlowLayout(), defaultWidth, defaultHeight);
    }

    public CustomPanel() {
        this(new FlowLayout());
    }

    public CustomPanel addTextField(String text, int width, int height, String id) {
        JTextField jTextField = new JTextField(text);
        SwingUtils.setPrompt(text, jTextField);
        jTextField.setPreferredSize(new Dimension(width, height));
        this.add(jTextField);
        this.textFields.put(id, jTextField);
        return this;
    }

    public CustomPanel addTextField(String text, String id) {
        return this.addTextField(text, this.defaultWidth, this.defaultHeight, id);
    }

    public String getText(String id) {
        return this.textFields.get(id).getText();
    }

    public JTextField getTextField(String id) {
        return this.textFields.get(id);
    }

    public CustomPanel addButton(String text, int width, int height, BiConsumer<JButton, ActionEvent> actionListener) {
        JButton jButton = new JButton(text);
        jButton.setPreferredSize(new Dimension(width, height));
        jButton.addActionListener(e -> actionListener.accept(jButton, e));
        this.add(jButton);
        return this;
    }

    public CustomPanel addButton(String text, BiConsumer<JButton, ActionEvent> actionListener) {
        return this.addButton(text, this.defaultWidth, this.defaultHeight, actionListener);
    }

    public <C extends Component> CustomPanel addComponent(Key<C> key, Supplier<C> cSupplier) {
        this.components.put(key, cSupplier.get());
        this.add(this.getComponent(key));
        return this;
    }

    public <C extends Component> CustomPanel addComponent(Supplier<C> cSupplier) {
        this.add(cSupplier.get());
        return this;
    }

    @SuppressWarnings("unchecked")
    public <C extends Component> C getComponent(Key<C> key) {
        return (C)this.components.get(key);
    }

    public static class Key<C> {
    }
}

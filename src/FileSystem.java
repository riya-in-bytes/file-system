import java.util.*;
import java.util.regex.*;

class FileSystem {
    private static class Dir {
        String name;
        Dir parent;
        Map<String, Dir> children = new HashMap<>();

        Dir(String name, Dir parent) {
            this.name = name;
            this.parent = parent;
        }

        String getPath() {
            if (parent == null) return "/";
            LinkedList<String> parts = new LinkedList<>();
            Dir current = this;
            while (current.parent != null) {
                parts.addFirst(current.name);
                current = current.parent;
            }
            return "/" + String.join("/", parts);
        }
    }

    private Dir root;
    private Dir current;

    public FileSystem() {
        root = new Dir("", null);
        current = root;
    }

    // mkdir /a/b/c
    public void mkdir(String path) {
        String[] parts = path.split("/");
        Dir node = path.startsWith("/") ? root : current;
        for (String part : parts) {
            if (part.isEmpty()) continue;
            node.children.putIfAbsent(part, new Dir(part, node));
            node = node.children.get(part);
        }
    }

    // cd pathWithRegex like cd /a/.* or cd .. or cd b
    public void cd(String path) {
        Dir node = path.startsWith("/") ? root : current;
        String[] parts = path.split("/");
        for (String part : parts) {
            if (part.isEmpty() || part.equals(".")) continue;
            if (part.equals("..")) {
                if (node.parent != null) node = node.parent;
                continue;
            }

            // regex match in children
            boolean matched = false;
            Pattern pattern = Pattern.compile(part);
            for (Dir child : node.children.values()) {
                if (pattern.matcher(child.name).matches()) {
                    node = child;
                    matched = true;
                    break;  // go to first match
                }
            }
            if (!matched) throw new RuntimeException("No matching directory for: " + part);
        }
        current = node;
    }

    // pwd
    public String pwd() {
        return current.getPath();
    }
}

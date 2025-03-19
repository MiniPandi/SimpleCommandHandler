@Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 0) return list;

        if (args.length == 1){
            for (int i = 1; i <= 5;i++){
                list.add(String.valueOf(i));
            }
        }
        ArrayList<String> current = new ArrayList<>();
        String currentarg = args[args.length-1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (s1.startsWith(currentarg)){
                current.add(s);
            }
        }
        return current;
    }

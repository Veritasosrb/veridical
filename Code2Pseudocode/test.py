def get_pseudocode_from_file(filename):
  with open(filename, 'r', encoding='utf-8') as f:
    code_lines = f.readlines()
    gen_pc = []
    max_len = 0
    new_snips = []
    
    for snippet in code_lines:
      snippet = snippet.replace("\n", "").replace("\t", "")
      if len(snippet.strip()) != 0:
        temp_len = len(snippet)
        pc = get_pseudocode(snippet)
        gen_pc.append(pc)
        new_snips.append(snippet)
      
        if max_len < temp_len:
          max_len = temp_len

  return new_snips, gen_pc, max_len
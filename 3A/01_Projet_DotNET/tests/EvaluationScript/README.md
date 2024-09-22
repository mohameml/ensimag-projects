# Instructions
There is one main document to be delivered for the evaluation. For group *i*, you will need to produce a zipped folder containing the entire solution with your code. The name of the folder should be *Group_i* and the name of the zipped file should be *Group_i.zip*.
  NB: remember to clean the source code before zipping the solution

The script that will be used for the evaluation is ``generate-backtest-results.py``. This script takes the following arguments (**all paths are absolute**):
- ``sln``: the path to the folder containing the zipped solutions
- ``tests``: the path to the folder contining the tests to be run (the path to 'TestCases', including the latter for the provided example)
- ``build``: the path to the folder where the solutions will be extracted and built
- ``out``: the path to the folder where the outputs produced by the code will be stored
- ``grpc`` (optional): the path to the folder where the grpc client is stored
- ``force``: a boolean argument stating whether the files in the build and output folders are overwritten or not

Command on Windows:
```bash
python.exe generate_backtest_results.py --sln=<abs-path-to-zipped-solutions> --tests=<abs-path-to-tests> --out=<abs-path-to-produced-outputs> --build=<abs-path-to-built-code> [--grpc=<abs-path-to-grpc-client>] --force
```
## Warning

The ``force`` argument erases the ``build`` and ``out`` folders if they already exist. It is **strongly** recommended to create new folders for the script arguments, to avoid mistakenly erasing anything useful.

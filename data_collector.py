import xlwt
import subprocess
import os
import matplotlib.pyplot as plt

def compile_run(dir):
    subprocess.call(['javac', 'Chromo.java', 'FitnessFunction.java',
    'Hprint.java', 'Hwrite.java', 'NumberMatch.java', 'Scheduling.java',
    'Parameters.java', 'Search.java', 'InputSchedule.java'], cwd=dir)

    subprocess.call(['java', 'Search', 'scheduling.params'], cwd=dir)

def compile_run_all():
    dir_path = os.path.dirname(os.path.realpath(__file__))
    for subdir, dirs, files in os.walk(dir_path):
        compile_run(subdir)

def experiment(dir, sheet_name):

    avgBest = write_values(dir + 'genAvgBest.txt')
    avgAvg = write_values(dir + 'genAvgAvg.txt')
    stdDevBest = write_values(dir + 'genStdDevBest.txt')
    stdDevAvg = write_values(dir + 'genStdDevAvg.txt')
    fig = plt.figure(figsize=(10,6))

    ax1 = plt.subplot(211)
    plt.title(sheet_name)
    plt.ylabel('Fitness')
    best = plt.plot(avgBest, label="Average Best")
    avg = plt.plot(avgAvg, label="Average Average")
    plt.legend()
    #plt.axis([0, 100, 0, 200])

    ax2 = plt.subplot(212, sharex=ax1)
    plt.title("Standard Deviation")
    plt.xlabel('Generations')
    plt.ylabel('Std Dev Fitness')
    sdb = plt.plot(stdDevBest, label="Std Dev Best")
    sda = plt.plot(stdDevAvg, label="Std Dev Average")
    plt.legend()

    fig.subplots_adjust(hspace=0.4, bottom=0.11, top=0.93)
    plt.show()

def write_values(filename):
    vals = []
    with open(filename) as ifile:
        for i,item in enumerate(ifile):
            vals.append(float(item.strip()))
    return vals

# compile_run_all()
dir_path = os.path.dirname(os.path.realpath(__file__))

experiment(dir_path + "/", 'Gray Coding')

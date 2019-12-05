            #ASSIGNMENT DESCRIPTION#

#   Problem: Shortest Cost Route to Navigate a Grid
#   Consider a grid where each cell has a different cost to travel across the regions. Assume we can only travel
#   and stop in straight lines between the corners of these cells. Note that the cost to travel along a border between
#   two cells is the cheapest of the two. We want to find the cheapest route from the lower-left corner to the upper-right
#   corner of the grid under these constraints.
#
#   For example in the following 3 × 3 grid, one of the cheapest routes of cost 2+3+4+2=11 is highlighted.
#
#   We will read in a sequence of problem instances. The first line will contain two positive integers n and m, 
#   both at most 400, denoting the dimensions of the grid; here the number of rows is n and the number of columns is m. 
#   We then are given n lines of m non-negative integers representing the costs for the cells. All integers will be 
#   separated by spaces. The last problem instance will have values of n = m = 0, which is not processed.
#
#   SAMPLE INPUT
#
#   3 3
#   0 6 2
#   1 8 4
#   2 3 7
#   3 5 
#   1 3 9 9 1 
#   5 10 1 8 4
#   2 7 8 2 6 
#   00
#
#   SAMPLE OUTPUT
#   11
#   17



import heapq

inp = str(input())   # graph size
min_dists = []
input_lists = []
while inp.split() != ["0", "0"]:
    input_list = []
    for t in range(int(inp.split()[0])):
        inp = str(input())   # vertex info
        input_list.append(inp.split())
    graph = dict()
    y_len = len(input_list)
    x_len = len(input_list[0])
    input_list.reverse()
    for y in range(len(input_list) + 1):
        for x in range(len(input_list[0]) + 1):
            edge = dict()
            if 0 < y < y_len:
                if 0 < x < x_len:
                    # diagonal edges
                    edge.update({str(x + 1) + "," + str(y + 1): int(input_list[y][x])})  # top right
                    edge.update({str(x - 1) + "," + str(y - 1): int(input_list[y - 1][x - 1])})  # bottom left
                    edge.update({str(x + 1) + "," + str(y - 1): int(input_list[y - 1][x])})  # top left
                    edge.update({str(x - 1) + "," + str(y + 1): int(input_list[y][x - 1])})  # bottom right

                    edge.update({str(x - 1) + "," + str(y): int(min(int(input_list[y - 1][x - 1]), int(input_list[y][x - 1])))})  # left
                    edge.update({str(x + 1) + "," + str(y): int(min(int(input_list[y][x]), int(input_list[y - 1][x])))})  # right
                    edge.update({str(x) + "," + str(y - 1): int(min(int(input_list[y - 1][x]), int(input_list[y - 1][x - 1])))})  # down
                    edge.update({str(x) + "," + str(y + 1): int(min(int(input_list[y][x]), int(input_list[y][x - 1])))})  # up
                elif x == 0:
                    edge.update({str(x) + "," + str(y + 1): int(input_list[y][x])})
                    edge.update({str(x) + "," + str(y - 1): int(input_list[y - 1][x])})
                    edge.update({str(x + 1) + "," + str(y + 1): int(input_list[y][x])})
                    edge.update({str(x + 1) + "," + str(y - 1): int(input_list[y - 1][x])})
                    edge.update({str(x + 1) + "," + str(y): int(min(int(input_list[y][x]), int(input_list[y - 1][x])))})

                else:
                    edge.update({str(x) + "," + str(y + 1): int(input_list[y][x - 1])})
                    edge.update({str(x) + "," + str(y - 1): int(input_list[y - 1][x - 1])})
                    edge.update({str(x - 1) + "," + str(y - 1): int(input_list[y - 1][x - 1])})
                    edge.update({str(x - 1) + "," + str(y + 1): int(input_list[y][x - 1])})
                    edge.update(
                        {str(x - 1) + "," + str(y): int(min(int(input_list[y - 1][x - 1]), int(input_list[y][x - 1])))})

            elif y == 0:
                if 0 < x < x_len:
                    edge.update({str(x - 1) + "," + str(y): int(input_list[y][x - 1])})
                    edge.update({str(x + 1) + "," + str(y): int(input_list[y][x])})
                    edge.update({str(x - 1) + "," + str(y + 1): int(input_list[y][x - 1])})
                    edge.update({str(x + 1) + "," + str(y + 1): int(input_list[y][x])})
                    edge.update({str(x) + "," + str(y + 1): int(min(int(input_list[y][x]), int(input_list[y][x - 1])))})

                elif x == 0:
                    edge.update({"0,1": int(input_list[0][0])})  # bottom left
                    edge.update({"1,0": int(input_list[0][0])})
                    edge.update({"1,1": int(input_list[0][0])})
                else:
                    edge.update({str(x - 1) + ",1": int(input_list[0][x - 1])})  # top right corner
                    edge.update({str(x - 1) + ",0": int(input_list[0][x - 1])})
                    edge.update({str(x) + ",1": int(input_list[0][x - 1])})
            else:
                if 0 < x < x_len:
                    edge.update({str(x - 1) + "," + str(y): int(input_list[y - 1][x - 1])})
                    edge.update({str(x + 1) + "," + str(y): int(input_list[y - 1][x])})
                    edge.update({str(x - 1) + "," + str(y - 1): int(input_list[y - 1][x - 1])})
                    edge.update({str(x + 1) + "," + str(y - 1): int(input_list[y - 1][x])})
                    edge.update(
                        {str(x) + "," + str(y - 1): int(min(int(input_list[y - 1][x]), int(input_list[y - 1][x - 1])))})
                elif x == 0:
                    edge.update({str(x + 1) + "," + str(y - 1): int(input_list[y - 1][0])})  # bottom left
                    edge.update({str(x + 1) + "," + str(y): int(input_list[y - 1][0])})
                    edge.update({str(x) + "," + str(y - 1): int(input_list[y - 1][0])})
                else:
                    edge.update({str(x - 1) + "," + str(y - 1): int(input_list[y - 1][x - 1])})  # bottom right
                    edge.update({str(x - 1) + "," + str(y): int(input_list[y - 1][x - 1])})
                    edge.update({str(x) + "," + str(y - 1): int(input_list[y - 1][x - 1])})




            graph.update({str(x) + "," + str(y): edge})
    for x, y in graph.items():
        print(x, y)

    weights = {}
    for v in graph:
        weights.update({v: float('inf')})
    weights['0,0'] = 0

    v_list = []
    for k,i in graph.items():
        v_list.append(k)

    while len(v_list) != 0:
        u = None
        min_key = float('inf')

        for v, w in weights.items():
            if v in v_list and w < min_key:
                min_key = w
                u = v

        v_list.remove(u)

        for n, w in graph[u].items():
            if n not in v_list:
                continue

            alt = weights[u] + w

            if alt < weights[n]:
                weights[n] = alt
    print(weights)
    min_dists.append(weights[str(len(input_list[0])) + ',' + str(len(input_list))])





    inp = str(input())  # vertex info
    input_lists.append(input_list)
print("\n".join([str(x) for x in min_dists]))


